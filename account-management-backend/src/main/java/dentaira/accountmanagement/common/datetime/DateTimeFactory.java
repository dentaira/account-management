package dentaira.accountmanagement.common.datetime;

import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class DateTimeFactory {

    private final Clock clock = Clock.systemDefaultZone();

    public Instant now() {
        return clock.instant().truncatedTo(ChronoUnit.SECONDS);
    }

}
