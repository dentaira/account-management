package dentaira.accountmanagement.web.member;

import dentaira.accountmanagement.member.MemberCreateCommand;
import dentaira.accountmanagement.member.MemberDTO;
import dentaira.accountmanagement.member.MemberUsecase;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberUsecase memberUsecase;

    @PostMapping
    public MemberDTO post(@RequestBody MemberCreateCommand command) {
        return memberUsecase.create(command);
    }

}
