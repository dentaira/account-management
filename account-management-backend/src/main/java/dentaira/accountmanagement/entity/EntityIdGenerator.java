package dentaira.accountmanagement.entity;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedEpochGenerator;
import org.springframework.stereotype.Component;

@Component
public class EntityIdGenerator {

    private static final TimeBasedEpochGenerator generator = Generators.timeBasedEpochGenerator();

    public <T> EntityId<T> generate() {
        return new EntityId<T>(generator.generate());
    }
}
