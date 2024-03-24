package dentaira.accountmanagement.common;

import jakarta.validation.constraints.Email;

public record EmailAddress(@Email String value) {

  public static EmailAddress of(String localPart, String domain) {
    return new EmailAddress(localPart + "@" + domain);
  }
}
