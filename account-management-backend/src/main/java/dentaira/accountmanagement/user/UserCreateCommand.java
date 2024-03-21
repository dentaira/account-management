package dentaira.accountmanagement.user;

import dentaira.accountmanagement.member.MemberId;

public record UserCreateCommand(MemberId memberId, String email, String name, UserRole role) {
}
