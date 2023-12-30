package executor.service.service.result;

import executor.service.dao.ScenarioResultRepository;
import executor.service.dao.StepResultRepository;
import executor.service.model.entity.ScenarioResult;
import executor.service.model.entity.StepResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JpaScenarioResultService
        implements ScenarioResultService {

    private final ScenarioResultRepository repository;

    private final StepResultRepository stepRepository;

    public JpaScenarioResultService(
            ScenarioResultRepository repository,
            StepResultRepository stepRepository) {
        this.repository = repository;
        this.stepRepository = stepRepository;
    }

    @Override
    @Transactional
    public ScenarioResult createScenarioResult(ScenarioResult result) {
        List<StepResult> stepResults = result.getStepResults();
        stepResults.forEach(stepResult -> stepResult.setExecutionMessage("Done"));
        stepRepository.saveAll(stepResults);
        return repository.save(result);
    }

    @Override
    public List<ScenarioResult> getAllScenarioResults() {
        return repository.findAll();
    }
}
