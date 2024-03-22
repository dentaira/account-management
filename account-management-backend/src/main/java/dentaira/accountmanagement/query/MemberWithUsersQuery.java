package dentaira.accountmanagement.query;

import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.UserDTO;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.List;

import static dentaira.accountmanagement.jooq.Members.MEMBERS;
import static dentaira.accountmanagement.jooq.Tables.USERS;
import static java.util.stream.Collectors.groupingBy;

@Component
@AllArgsConstructor
public class MemberWithUsersQuery {

    public record MemberWithUsersDTO(MemberId memberId, String companyName, List<UserDTO> users) {
    }

    private final DSLContext context;

    public List<MemberWithUsersDTO> fetch() {
        var sql = context
                .select()
                .from(MEMBERS)
                .join(USERS).on(MEMBERS.MEMBER_ID.eq(USERS.MEMBER_ID));

        return sql.fetchStream()
                .collect(groupingBy(r -> r.get(MEMBERS.MEMBER_ID)))
                .entrySet()
                .stream()
                .map(entry -> {
                    var memberId = entry.getKey();
                    var companyName = entry.getValue().getFirst().get(MEMBERS.COMPANY_NAME); // 必ず存在する
                    var users = entry.getValue()
                            .stream()
                            .map(DTOConverters::toUserDTO)
                            .toList();
                    return new MemberWithUsersDTO(new MemberId(memberId), companyName, users);
                })
                .toList();
    }

}
