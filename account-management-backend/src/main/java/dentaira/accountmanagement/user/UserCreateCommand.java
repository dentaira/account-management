package dentaira.accountmanagement.user;

public record UserCreateCommand(String emailLocal, String emailDomain, String name, UserRole role) {
}
