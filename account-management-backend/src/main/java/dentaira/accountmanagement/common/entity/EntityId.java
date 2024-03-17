package dentaira.accountmanagement.common.entity;

import java.util.UUID;

@SuppressWarnings("unused")
public record EntityId<T>(UUID value) {
}
