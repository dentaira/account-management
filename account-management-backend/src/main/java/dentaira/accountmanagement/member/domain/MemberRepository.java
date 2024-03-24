package dentaira.accountmanagement.member.domain;

import dentaira.accountmanagement.member.MemberId;
import java.util.Optional;

public interface MemberRepository {

  MemberId generateId();

  Optional<Member> findById(MemberId member);

  void save(Member member);

  Member update(Member member);
}
