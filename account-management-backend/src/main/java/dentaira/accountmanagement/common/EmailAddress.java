package dentaira.accountmanagement.common;

public record EmailAddress(String value) {

    public static EmailAddress of(String local, String domain) {
        return new EmailAddress(local + "@" + domain);
    }

}
