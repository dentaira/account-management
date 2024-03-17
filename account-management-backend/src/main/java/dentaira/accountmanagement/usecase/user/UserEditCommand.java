package dentaira.accountmanagement.usecase.user;

import dentaira.accountmanagement.common.entity.EntityId;
import dentaira.accountmanagement.user.User;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;

public record UserEditCommand(EntityId<User> userId, String newName, UserRole newRole, UserStatus newStatus) {
}
