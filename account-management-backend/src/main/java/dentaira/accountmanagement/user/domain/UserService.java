package dentaira.accountmanagement.user.domain;

import dentaira.accountmanagement.common.DateTimeFactory;
import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.UnregisteredUserEmailAddress;
import dentaira.accountmanagement.user.UserId;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/** ユーザーを更新するドメインロジックを提供する */
@Service
@AllArgsConstructor
public class UserService {

  private final DateTimeFactory dateTimeFactory;

  /**
   * ユーザーを作成する
   *
   * @param userId ユーザーID
   * @param memberId 会員ID
   * @param email ユーザー登録に使用可能なメールアドレス
   * @param encryptedPassword 暗号化されたパスワード
   * @param name 名前
   * @param role ユーザー権限
   * @return ユーザー
   */
  public User create(
      UserId userId,
      MemberId memberId,
      UnregisteredUserEmailAddress email,
      String encryptedPassword,
      String name,
      UserRole role) {
    var now = dateTimeFactory.now();
    return new User(
        userId,
        memberId,
        email.email(),
        encryptedPassword,
        name,
        role,
        UserStatus.Active,
        1,
        now,
        now);
  }

  /**
   * ユーザーを編集する
   *
   * @param user ユーザー
   * @param name 名前
   * @param role ユーザー権限
   * @param activate 有効化する場合はtrue
   * @return 編集後のユーザー
   */
  public User edit(User user, String name, UserRole role, boolean activate) {
    var newStatus = activate ? UserStatus.Active : UserStatus.Inactive;
    if (!user.status().canTransitTo(newStatus)) {
      throw new IllegalArgumentException(
          "Invalid status transition. " + user.status() + " -> " + newStatus);
    }
    return user.toBuilder()
        .withName(name)
        .withRole(role)
        .withStatus(newStatus)
        .withUpdatedAt(dateTimeFactory.now())
        .build();
  }

  /**
   * メールアドレスを変更する
   *
   * @param user ユーザー
   * @param email メールアドレス
   * @return 変更後のユーザー
   */
  public User changeEmail(User user, EmailAddress email) {
    return user.toBuilder().withEmail(email).withUpdatedAt(dateTimeFactory.now()).build();
  }
}
