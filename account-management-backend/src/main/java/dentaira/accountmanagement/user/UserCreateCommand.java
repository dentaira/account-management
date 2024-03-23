package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.MemberId;
import jakarta.validation.Valid;

public record UserCreateCommand(MemberId memberId, @Valid EmailAddress email, String name, UserRole role) {
}
