package executor.service.aop;

import executor.service.model.entity.ScenarioResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LogManager.getLogger(LoggingAspect.class);

    @AfterReturning(
            returning = "scenarioResult",
            pointcut = "Pointcuts.scenarioExecutorServicePointCut()")
    public void logScenarioResultAfterExecutionAspect(
            final ScenarioResult scenarioResult) {
        logger.info("Scenario result has been executed: {}", scenarioResult);
    }
}
