package dentaira.accountmanagement.entity;

import java.util.UUID;

@SuppressWarnings("unused")
public record EntityId<T>(UUID value) {

    public static <T> EntityId<T> of(UUID value) {
        return new EntityId<>(value);
    }

    public static <T> EntityId<T> of(String value) {
        return new EntityId<>(UUID.fromString(value));
    }
}
