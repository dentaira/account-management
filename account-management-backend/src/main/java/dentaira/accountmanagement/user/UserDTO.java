package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.domain.User;
import java.time.Instant;

public record UserDTO(
    UserId userId,
    MemberId memberId,
    EmailAddress email,
    String name,
    UserRole role,
    UserStatus status,
    int version,
    Instant createdAt,
    Instant updatedAt) {

  static UserDTO from(User user) {
    return new UserDTO(
        user.userId(),
        user.memberId(),
        user.email(),
        user.name(),
        user.role(),
        user.status(),
        user.version(),
        user.createdAt(),
        user.updatedAt());
  }
}
