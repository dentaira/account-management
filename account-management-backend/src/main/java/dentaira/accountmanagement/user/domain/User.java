package dentaira.accountmanagement.user.domain;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.UserId;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(toBuilder = true, access = AccessLevel.PACKAGE, setterPrefix = "with")
public record User(
    UserId userId,
    MemberId memberId,
    EmailAddress email,
    String name,
    UserRole role,
    UserStatus status,
    int version,
    Instant createdAt,
    Instant updatedAt) {}
