package dentaira.accountmanagement.usecase.user;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.entity.EntityId;
import dentaira.accountmanagement.entity.EntityNotFoundException;
import dentaira.accountmanagement.user.User;
import dentaira.accountmanagement.user.UserRepository;
import dentaira.accountmanagement.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserUsecase {

    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * fixme create password
     */
    public UserDTO create(UserCreateCommand command) {
        var email = EmailAddress.of(command.emailLocal(), command.emailDomain());

        userRepository.findByEmail(email).ifPresent(user -> {
            throw new IllegalArgumentException("User already exists. " + email.value());
        });

        var createdUser = userService.create(email, command.name(), command.role());

        userRepository.save(createdUser);

        return UserDTO.from(createdUser);
    }

    /**
     * fixme 実際の値が変更されていなくても更新日時とバージョンが更新される
     */
    public UserDTO edit(EntityId<User> userId, UserEditCommand command) {
        var targetUser = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);

        var editedUser = userService.edit(targetUser, command.name(), command.role(), command.status());

        var savedUser = userRepository.update(editedUser);

        return UserDTO.from(savedUser);
    }
}
