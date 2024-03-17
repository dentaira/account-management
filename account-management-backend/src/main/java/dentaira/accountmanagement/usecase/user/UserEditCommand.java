package dentaira.accountmanagement.usecase.user;

import dentaira.accountmanagement.common.entity.EntityId;
import dentaira.accountmanagement.user.User;
import dentaira.accountmanagement.user.UserService;

public record UserEditCommand(EntityId<User> userId, UserService.UserEdit userEdit) {
}
