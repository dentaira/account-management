package dentaira.accountmanagement.usecase.user;

import dentaira.accountmanagement.user.UserRole;

public record UserCreateCommand(String emailLocal, String emailDomain, String name, UserRole role) {
}
