package executor.service.mapper;

import executor.service.model.dto.ScenarioResultResponse;
import executor.service.model.entity.ScenarioResult;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {StepMapper.class}
)
public interface ScenarioMapper {

    ScenarioResultResponse scenarioResultToScenarioResponse(ScenarioResult result);
    List<ScenarioResultResponse> scenarioResultToScenarioResponse(List<ScenarioResult> result);
}
