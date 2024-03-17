package dentaira.accountmanagement.usecase.user;

import dentaira.accountmanagement.common.entity.EntityId;
import dentaira.accountmanagement.common.email.EmailAddress;
import dentaira.accountmanagement.user.User;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;

import java.time.Instant;

public record UserDTO(EntityId<User> userId,
                      EmailAddress email,
                      String name,
                      UserRole role,
                      UserStatus status,
                      int version,
                      Instant createdAt,
                      Instant updatedAt) {

    public static UserDTO from(User user) {
        return new UserDTO(user.userId(),
                user.email(),
                user.name(),
                user.role(),
                user.status(),
                user.version(),
                user.createdAt(),
                user.updatedAt());
    }
}
