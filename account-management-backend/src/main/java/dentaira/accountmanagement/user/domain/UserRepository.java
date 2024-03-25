package dentaira.accountmanagement.user.domain;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.common.EntityUpdateConflictException;
import dentaira.accountmanagement.user.UserId;
import java.util.Optional;

public interface UserRepository {

  /**
   * ユーザーIDを生成する
   *
   * @return ユーザーID
   */
  UserId generateId();

  /**
   * ユーザーIDでユーザーを検索する
   *
   * @param userId ユーザーID
   * @return ユーザー
   */
  Optional<User> findById(UserId userId);

  /**
   * メールアドレスでユーザーを検索する
   *
   * @param email メールアドレス
   * @return ユーザー
   */
  Optional<User> findByEmail(EmailAddress email);

  /**
   * ユーザーを保存する
   *
   * @param user ユーザー
   */
  void save(User user);

  /**
   * ユーザーを更新する
   *
   * @param user ユーザー
   * @return 更新後のユーザー
   * @throws EntityUpdateConflictException バージョンが一致しない場合
   */
  User update(User user);
}
