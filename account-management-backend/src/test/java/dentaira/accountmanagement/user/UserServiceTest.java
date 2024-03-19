package dentaira.accountmanagement.user;

import dentaira.accountmanagement.common.DateTimeFactory;
import dentaira.accountmanagement.common.EmailAddress;
import dentaira.accountmanagement.entity.EntityId;
import dentaira.accountmanagement.entity.EntityIdGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@MockitoSettings
class UserServiceTest {

    @Mock
    private EntityIdGenerator entityIdGenerator;

    @Mock
    private DateTimeFactory dateTimeFactory;

    @InjectMocks
    private UserService sut;

    @Test
    public void testEdit() {
        // given
        var source = new User(
                new EntityId<>(UUID.randomUUID()),
                EmailAddress.of("local", "example.com"),
                "oldName",
                UserRole.User,
                UserStatus.Inactive,
                0,
                Instant.EPOCH,
                Instant.ofEpochSecond(1234567890)
        );

        when(dateTimeFactory.now()).thenReturn(Instant.ofEpochSecond(1234567899));

        // when
        var actual = sut.edit(source, "name", UserRole.Admin, UserStatus.Active);

        // then
        assertThat(actual).isEqualTo(new User(
                source.userId(),
                source.email(),
                "name",
                UserRole.Admin,
                UserStatus.Active,
                source.version(),
                source.createdAt(),
                Instant.ofEpochSecond(1234567899)
        ));
    }
}