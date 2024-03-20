package dentaira.accountmanagement.user;

public record UserEditCommand(String name, UserRole role, boolean activate) {
}
