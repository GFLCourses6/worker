package executor.service.holder;

import executor.service.model.dto.Scenario;
import org.springframework.stereotype.Component;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class ScenarioQueueHolder implements QueueHolder<Scenario> {

    private final BlockingQueue<Scenario> scenarios = new LinkedBlockingQueue<>();

    @Override
    public BlockingQueue<Scenario> getQueue() {
        return scenarios;
    }
}
