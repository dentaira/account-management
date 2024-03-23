package dentaira.accountmanagement.common;

public record EmailAddress(String value) {

    public static EmailAddress of(String localPart, String domain) {
        return new EmailAddress(localPart + "@" + domain);
    }

}
