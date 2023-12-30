package executor.service.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* executor.service.service.executor.ScenarioExecutorService.execute(..))")
    public void scenarioExecutorServicePointCut() {
    }
}
