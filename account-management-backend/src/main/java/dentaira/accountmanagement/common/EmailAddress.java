package dentaira.accountmanagement.common;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.Email;

public record EmailAddress(@JsonValue @Email String value) {

  public static EmailAddress of(String localPart, String domain) {
    return new EmailAddress(localPart + "@" + domain);
  }
}
