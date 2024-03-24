package dentaira.accountmanagement.query;

import static dentaira.accountmanagement.jooq.Tables.USERS;

import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.member.MemberId;
import dentaira.accountmanagement.user.UserDTO;
import dentaira.accountmanagement.user.UserId;
import dentaira.accountmanagement.user.UserRole;
import dentaira.accountmanagement.user.UserStatus;
import org.jooq.Record;

/**
 * インフラ層のライブラリのコードからDTOへの変換処理を提供する
 *
 * <p>複数のQueryで扱わないDTOの変換処理は、各Queryクラス内に記述すること
 */
public class DTOConverters {

  static UserDTO toUserDTO(Record r) {
    return new UserDTO(
        new UserId(r.get(USERS.USER_ID)),
        new MemberId(r.get(USERS.MEMBER_ID)),
        new EmailAddress(r.get(USERS.EMAIL)),
        r.get(USERS.USER_NAME),
        UserRole.valueOf(r.get(USERS.ROLE)),
        UserStatus.valueOf(r.get(USERS.STATUS)),
        r.get(USERS.VERSION),
        r.get(USERS.CREATED_AT),
        r.get(USERS.UPDATED_AT));
  }
}
