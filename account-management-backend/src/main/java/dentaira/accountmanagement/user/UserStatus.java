package dentaira.accountmanagement.user;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserStatus {
  Init("未ログイン"),
  Active("利用中"),
  Inactive("利用停止中"),
  Deleted("削除済み");

  public final String displayName;

  public boolean canTransitTo(UserStatus newStatus) {
    if (this == newStatus) {
      return true;
    }
    return switch (this) {
      case Init -> newStatus == Active;
      case Active -> newStatus == Inactive || newStatus == Deleted;
      case Inactive -> newStatus == Active || newStatus == Deleted;
      case Deleted -> false;
    };
  }
}
