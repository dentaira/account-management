package dentaira.accountmanagement.member;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.domain.Member;
import java.time.Instant;

public record MemberDTO(
    MemberId memberId,
    String companyName,
    String departmentName,
    MemberStatus status,
    EmailAddress email,
    int version,
    Instant createdAt,
    Instant updatedAt) {

  static MemberDTO from(Member member) {
    return new MemberDTO(
        member.memberId(),
        member.companyName(),
        member.departmentName(),
        member.status(),
        member.email(),
        member.version(),
        member.createdAt(),
        member.updatedAt());
  }
}
