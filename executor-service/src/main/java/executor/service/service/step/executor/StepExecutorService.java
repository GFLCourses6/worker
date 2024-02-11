package executor.service.service.step.executor;

import executor.service.exception.StepNotFoundException;
import executor.service.model.dto.Step;
import executor.service.model.entity.ExecutionStatus;
import executor.service.model.entity.StepResult;
import executor.service.service.step.StepExecution;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class StepExecutorService implements StepExecutor {

    private final Map<String, StepExecution> availableSteps;

    @Autowired
    public StepExecutorService(List<StepExecution> steps) {
        this.availableSteps = steps.stream().collect(toMap(
                StepExecution::getStepAction, identity()));
    }

    @Override
    public StepResult makeStep(WebDriver driver, Step step) {
        try {
            return getStepExecution(step).step(driver, step);
        } catch (Exception e) {
            return new StepResult(step, e.getMessage(), ExecutionStatus.FAIL);
        }
    }

    private StepExecution getStepExecution(Step step) {
        return Optional.ofNullable(availableSteps.get(step.getAction()))
                .orElseThrow(() -> new StepNotFoundException(step));
    }
}
