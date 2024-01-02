package executor.service.aop;

import executor.service.model.entity.ScenarioResult;
import executor.service.service.scenario.result.ScenarioResultService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LogManager.getLogger(LoggingAspect.class);

    private final ScenarioResultService scenarioResultService;

    public LoggingAspect(
            final ScenarioResultService resultService) {
        this.scenarioResultService = resultService;
    }

    @AfterReturning(
            returning = "scenarioResult",
            pointcut = "Pointcuts.scenarioExecutorServicePointCut()")
    public void saveScenarioResultAfterExecutionAspect(
            final ScenarioResult scenarioResult) {
        scenarioResultService.createScenarioResult(scenarioResult);
        logger.info("Saved after execution: {}", scenarioResult);
    }
}
