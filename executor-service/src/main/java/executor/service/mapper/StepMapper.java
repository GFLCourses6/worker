package executor.service.mapper;

import executor.service.model.dto.StepResultResponse;
import executor.service.model.entity.StepResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StepMapper {

    @Mapping(target = "action", source = "step.action")
    @Mapping(target = "value", source = "step.value")
    StepResultResponse stepResultToStepResultResponse(StepResult stepResult);
}
