package dentaira.accountmanagement.member;

import java.util.UUID;

public record MemberId(UUID value) {

    public static MemberId of(UUID value) {
        return new MemberId(value);
    }

    public static MemberId of(String value) {
        return new MemberId(UUID.fromString(value));
    }

}
