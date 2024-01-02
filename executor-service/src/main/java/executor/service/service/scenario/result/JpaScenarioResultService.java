package executor.service.service.scenario.result;

import executor.service.dao.ScenarioResultRepository;
import executor.service.mapper.ScenarioMapper;
import executor.service.model.dto.ScenarioResultResponse;
import executor.service.model.entity.ScenarioResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JpaScenarioResultService
        implements ScenarioResultService {

    private final ScenarioResultRepository repository;
    private final ScenarioMapper scenarioMapper;

    public JpaScenarioResultService(ScenarioResultRepository repository,
                                    ScenarioMapper scenarioMapper) {
        this.repository = repository;
        this.scenarioMapper = scenarioMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ScenarioResultResponse createScenarioResult(
            final ScenarioResult result) {
        var scenarioResult = repository.save(result);
        return scenarioMapper.scenarioResultToScenarioResponse(scenarioResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScenarioResultResponse> getAllScenarioResults() {
        return scenarioMapper.scenarioResultToScenarioResponse(repository.findAllFetchStepResults());
    }
}
