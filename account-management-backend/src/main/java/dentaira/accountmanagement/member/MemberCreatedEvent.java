package dentaira.accountmanagement.member;

import dentaira.accountmanagement.common.EmailAddress;

public record MemberCreatedEvent(MemberId memberId, String applicantName, EmailAddress email) {}
