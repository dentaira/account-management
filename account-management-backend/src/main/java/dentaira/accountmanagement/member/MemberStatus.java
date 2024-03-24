package dentaira.accountmanagement.member;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MemberStatus {
  Provisional("仮登録済み"),
  Active("利用中"),
  Recess("休会中"),
  Deleted("退会済み");

  public final String displayName;
}
