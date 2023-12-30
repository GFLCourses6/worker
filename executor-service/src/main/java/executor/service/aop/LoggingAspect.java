package executor.service.aop;

import executor.service.model.entity.ScenarioResult;
import executor.service.service.result.ScenarioResultService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LogManager.getLogger(LoggingAspect.class);

    private final ScenarioResultService scenarioResultService;

    public LoggingAspect(ScenarioResultService scenarioResultService) {
        this.scenarioResultService = scenarioResultService;
    }

    @AfterReturning(pointcut = "Pointcuts.scenarioSourceListenerPointCut()", returning = "scenarioResult")
    public void saveScenarioResultAfterExecutionAspect(ScenarioResult scenarioResult) {
        scenarioResultService.createScenarioResult(scenarioResult);
        logger.info("save ScenarioResult After Execution Aspect: {}",
                    scenarioResult);
    }

    @Around("Pointcuts.scenarioExecutorServicePointCut()")
    public Object logExecuteProceed(ProceedingJoinPoint point) {
        String name = point.getSignature().getName();
        String className = point.getTarget().getClass().toString();
        Object[] array = point.getArgs();
        logger.info("{}", array);
        Object proceed;
        try {
            proceed = point.proceed();
            ScenarioResult result = (ScenarioResult) proceed;
            logger.info("{} {} {}", name, className, result);
            scenarioResultService.createScenarioResult(result);
        } catch (Throwable e) {
            proceed = null;
        }

        return proceed;
    }
}
