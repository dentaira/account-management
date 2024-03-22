package dentaira.accountmanagement.query;

import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.UserDTO;

import java.util.List;

public record MemberWithUsersDTO(MemberId memberId, String companyName, List<UserDTO> users) {
}
