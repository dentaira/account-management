package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.email.EmailAddress;
import dentaira.accountmanagement.common.entity.EntityId;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true, access = AccessLevel.PACKAGE, setterPrefix = "with")
public record User(
        EntityId<User> userId,
        EmailAddress email,
        String name,
        UserRole role,
        UserStatus status,
        int version,
        Instant createdAt,
        Instant updatedAt) {
}
