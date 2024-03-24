package dentaira.accountmanagement.user;

import java.util.UUID;

public record UserId(UUID value) {

  public static UserId of(UUID value) {
    return new UserId(value);
  }

  public static UserId of(String value) {
    return new UserId(UUID.fromString(value));
  }
}
