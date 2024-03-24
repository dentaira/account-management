package dentaira.accountmanagement.user.domain;

import dentaira.accountmanagement.common.DateTimeFactory;
import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.UserId;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final DateTimeFactory dateTimeFactory;

  public User create(
      UserId userId,
      MemberId memberId,
      EmailAddress email,
      String encryptedPassword,
      String name,
      UserRole role) {
    var now = dateTimeFactory.now();
    return new User(
        userId, memberId, email, encryptedPassword, name, role, UserStatus.Active, 1, now, now);
  }

  public User edit(User user, String name, UserRole role, boolean activate) {
    var newStatus = activate ? UserStatus.Active : UserStatus.Inactive;
    if (!user.status().canTransitTo(newStatus)) {
      throw new IllegalArgumentException(
          "Invalid status transition. " + user.status() + " -> " + newStatus);
    }
    return user.toBuilder()
        .withName(name)
        .withRole(role)
        .withStatus(newStatus)
        .withUpdatedAt(dateTimeFactory.now())
        .build();
  }

  public User changeEmail(User user, EmailAddress email) {
    return user.toBuilder().withEmail(email).withUpdatedAt(dateTimeFactory.now()).build();
  }
}
