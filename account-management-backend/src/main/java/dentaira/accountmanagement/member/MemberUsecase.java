package dentaira.accountmanagement.member;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.domain.MemberRepository;
import dentaira.accountmanagement.member.domain.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MemberUsecase {

  private final MemberService memberService;
  private final MemberRepository memberRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Transactional
  public MemberDTO create(MemberCreateCommand command) {
    var email = new EmailAddress(command.email());
    var memberId = memberRepository.generateId();
    var member =
        memberService.create(memberId, command.companyName(), command.departmentName(), email);

    memberRepository.save(member);

    eventPublisher.publishEvent(
        new MemberCreatedEvent(member.memberId(), command.applicantName(), email));

    return MemberDTO.from(member);
  }
}
