package dentaira.accountmanagement.member;

import dentaira.accountmanagement.common.EmailAddress;
import jakarta.validation.Valid;
import java.util.Optional;

public record MemberCreateCommand(
    String companyName,
    Optional<String> departmentName,
    @Valid EmailAddress email,
    String applicantName,
    String passwordForFirstUser) {}
