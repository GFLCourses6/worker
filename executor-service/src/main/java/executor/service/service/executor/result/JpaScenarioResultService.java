package executor.service.service.executor.result;

import executor.service.dao.ScenarioResultRepository;
import executor.service.model.entity.ScenarioResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JpaScenarioResultService
        implements ScenarioResultService {

    private final ScenarioResultRepository repository;

    public JpaScenarioResultService(
            final ScenarioResultRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ScenarioResult createScenarioResult(
            final ScenarioResult result) {
        return repository.save(result);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScenarioResult> getAllScenarioResults() {
        return repository.findAllFetchStepResults();
    }
}
