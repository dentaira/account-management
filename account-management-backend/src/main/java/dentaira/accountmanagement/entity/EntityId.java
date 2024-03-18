package dentaira.accountmanagement.entity;

import java.util.UUID;

@SuppressWarnings("unused")
public record EntityId<T>(UUID value) {
}
