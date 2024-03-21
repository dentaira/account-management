package dentaira.accountmanagement.member;

public record MemberCreateCommand(String companyName, String departmentName, String email, String applicantName) {
}
