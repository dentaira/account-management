package dentaira.accountmanagement.member.domain;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.member.MemberStatus;
import java.time.Instant;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(toBuilder = true, access = AccessLevel.PACKAGE, setterPrefix = "with")
public record Member(
    MemberId memberId,
    String companyName,
    Optional<String> departmentName,
    MemberStatus status,
    EmailAddress email,
    int version,
    Instant createdAt,
    Instant updatedAt) {}
