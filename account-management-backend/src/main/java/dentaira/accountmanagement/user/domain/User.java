package dentaira.accountmanagement.user.domain;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.user.UserId;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;
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