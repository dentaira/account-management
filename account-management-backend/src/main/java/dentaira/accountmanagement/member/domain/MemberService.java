package dentaira.accountmanagement.member.domain;

import dentaira.accountmanagement.common.DateTimeFactory;
import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.member.MemberStatus;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberService {

  private final DateTimeFactory dateTimeFactory;

  public Member create(
      MemberId memberId, String companyName, Optional<String> departmentName, EmailAddress email) {
    var now = dateTimeFactory.now();
    return new Member(
        memberId, companyName, departmentName, MemberStatus.Provisional, email, 1, now, now);
  }
}
