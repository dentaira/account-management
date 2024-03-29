package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.common.EntityNotFoundException;
import dentaira.accountmanagement.member.MemberCreatedEvent;
import dentaira.accountmanagement.user.domain.UserRepository;
import dentaira.accountmanagement.user.domain.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserUsecase {

  private final UserService userService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserDTO create(UserCreateCommand command) {
    var rawPassword = "password"; // fixme 仮パスワード
    return doCreate(command, rawPassword);
  }

  @EventListener
  public void onMemberCreated(MemberCreatedEvent event) {
    var command =
        new UserCreateCommand(
            event.memberId(), event.email(), event.applicantName(), UserRole.Admin);
    doCreate(command, event.rawPasswordForFirstUser());
  }

  private UserDTO doCreate(UserCreateCommand command, String rawPassword) {
    var unregisteredEmail = validateUnregisteredEmail(command.email());

    var userId = userRepository.generateId();
    var encryptedPassword = passwordEncoder.encode(rawPassword);
    var createdUser =
        userService.create(
            userId,
            command.memberId(),
            unregisteredEmail,
            encryptedPassword,
            command.name(),
            command.role());

    userRepository.save(createdUser);

    return UserDTO.from(createdUser);
  }

  public UnregisteredUserEmailAddress validateUnregisteredEmail(EmailAddress email) {
    if (userRepository.findByEmail(email).isPresent()) {
      throw new IllegalArgumentException("User already exists. " + email.value());
    } else {
      return new UnregisteredUserEmailAddress(email);
    }
  }

  /** fixme 実際の値が変更されていなくても更新日時とバージョンが更新される */
  public UserDTO edit(UserId userId, UserEditCommand command) {
    var targetUser = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

    var editedUser =
        userService.edit(targetUser, command.name(), command.role(), command.activate());

    var savedUser = userRepository.update(editedUser);

    return UserDTO.from(savedUser);
  }

  public UserDTO changeEmail(UserId userId, EmailAddress email) {
    var targetUser = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

    var changedUser = userService.changeEmail(targetUser, email);

    var savedUser = userRepository.update(changedUser);

    return UserDTO.from(savedUser);
  }
}
