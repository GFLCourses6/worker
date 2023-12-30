package executor.service.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* executor.service.facade.parallel.ParallelFlowExecutorServiceImpl.execute(..))")
    public void scenarioSourceListenerPointCut() {
    }

    @Pointcut("execution(* executor.service.service.executor.ScenarioExecutorService.execute(..))")
    public void scenarioExecutorServicePointCut() {
    }


    @Pointcut("execution(* executor.service.facade.execution.ExecutionServiceImpl.execute(..))")
    public void executeScenarioPointCut() {
    }
}
