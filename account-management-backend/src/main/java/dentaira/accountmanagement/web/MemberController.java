package dentaira.accountmanagement.web;

import dentaira.accountmanagement.member.MemberCreateCommand;
import dentaira.accountmanagement.member.MemberDTO;
import dentaira.accountmanagement.member.MemberUsecase;
import dentaira.accountmanagement.query.MemberWithUsersQuery;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberUsecase memberUsecase;
    private final MemberWithUsersQuery memberWithUsersQuery;

    @GetMapping
    public List<MemberWithUsersQuery.MemberWithUsersDTO> get() {
        return memberWithUsersQuery.fetch();
    }

    @PostMapping
    public MemberDTO post(@RequestBody MemberCreateCommand command) {
        return memberUsecase.create(command);
    }
}