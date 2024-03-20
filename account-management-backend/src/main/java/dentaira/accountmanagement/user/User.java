package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.EmailAddress;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;

@Builder(toBuilder = true, access = AccessLevel.PACKAGE, setterPrefix = "with")
public record User(
        UserId userId,
        EmailAddress email,
        String name,
        UserRole role,
        UserStatus status,
        int version,
        Instant createdAt,
        Instant updatedAt) {
}
