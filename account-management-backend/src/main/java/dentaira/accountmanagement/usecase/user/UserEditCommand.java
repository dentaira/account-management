package dentaira.accountmanagement.usecase.user;

import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;

public record UserEditCommand(String name, UserRole role, boolean activate) {
}
