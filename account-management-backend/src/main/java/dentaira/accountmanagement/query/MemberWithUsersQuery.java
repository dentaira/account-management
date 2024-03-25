package dentaira.accountmanagement.query;

import static dentaira.accountmanagement.jooq.Members.MEMBERS;
import static dentaira.accountmanagement.jooq.Tables.USERS;

import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.UserDTO;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MemberWithUsersQuery {

  public record MemberWithUsersDTO(MemberId memberId, String companyName, List<UserDTO> users) {}

  private final DSLContext context;

  public List<MemberWithUsersDTO> fetch() {
    var sql = context.select().from(MEMBERS).join(USERS).on(MEMBERS.MEMBER_ID.eq(USERS.MEMBER_ID));

    return sql.fetchGroups(MEMBERS.MEMBER_ID).entrySet().stream()
        .map(
            entry -> {
              var memberId = entry.getKey();
              var companyName = entry.getValue().getFirst().get(MEMBERS.COMPANY_NAME); // 必ず存在する
              var users = entry.getValue().stream().map(DTOConverters::toUserDTO).toList();
              return new MemberWithUsersDTO(new MemberId(memberId), companyName, users);
            })
        .toList();
  }
}
