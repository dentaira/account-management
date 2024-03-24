package dentaira.accountmanagement.member;

import dentaira.accountmanagement.common.EmailAddress;
import jakarta.validation.Valid;

public record MemberCreateCommand(
    String companyName,
    String departmentName,
    @Valid EmailAddress email,
    String applicantName,
    String passwordForFirstUser) {}
