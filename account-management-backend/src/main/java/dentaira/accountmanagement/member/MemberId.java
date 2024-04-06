package dentaira.accountmanagement.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.UUID;

public record MemberId(@JsonValue UUID value) {

  @JsonCreator
  public MemberId {}

  public static MemberId of(String value) {
    return new MemberId(UUID.fromString(value));
  }
}
