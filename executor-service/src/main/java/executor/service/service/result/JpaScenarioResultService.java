package executor.service.service.result;

import executor.service.dao.ScenarioResultRepository;
import executor.service.model.entity.ScenarioResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class JpaScenarioResultService
        implements ScenarioResultService {

    private final ScenarioResultRepository repository;


    public JpaScenarioResultService(
            ScenarioResultRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public ScenarioResult createScenarioResult(ScenarioResult result) {
        return repository.save(result);
    }

    @Override
    public List<ScenarioResult> getAllScenarioResults() {
        return repository.findAllFetchStepResults();
    }
}
