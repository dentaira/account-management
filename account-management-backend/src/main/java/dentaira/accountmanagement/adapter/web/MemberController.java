package dentaira.accountmanagement.adapter.web;

import dentaira.accountmanagement.member.MemberCreateCommand;
import dentaira.accountmanagement.member.MemberDTO;
import dentaira.accountmanagement.member.MemberUsecase;
import dentaira.accountmanagement.query.MemberWithUsersQuery;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
  public MemberDTO post(@RequestBody @Valid MemberCreateCommand command) {
    return memberUsecase.create(command);
  }
}
