package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.entity.EntityId;
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
