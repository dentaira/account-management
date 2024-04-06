package dentaira.accountmanagement.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;

public record UserId(@JsonValue UUID value) {

  @JsonCreator
  public UserId {}

  public static UserId of(String value) {
    return new UserId(UUID.fromString(value));
  }
}
