package dentaira.accountmanagement.config;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdConfig {

    @Bean
    public TimeBasedEpochGenerator idGenerator() {
        return Generators.timeBasedEpochGenerator();
    }

}
