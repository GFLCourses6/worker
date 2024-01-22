package executor.service.mapper;

import executor.service.model.dto.Step;
import executor.service.model.entity.ExecutionStatus;
import executor.service.model.entity.StepResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StepMapperTest {

    @Autowired
    private StepMapper stepMapper;

    @ParameterizedTest
    @MethodSource("stepMapperArgsSource")
    void testStepMapper(StepResult source) {
        var actual = stepMapper.stepResultToStepResultResponse(source);

        assertEquals(source.getStep().getValue(), actual.getValue());
        assertEquals(source.getStep().getAction(), actual.getAction());
        assertEquals(source.getId(), actual.getId());
        assertEquals(source.getCreatedDate(), actual.getCreatedDate());
        assertEquals(source.getExecutionStatus(), actual.getExecutionStatus());
        assertEquals(source.getExecutionMessage(), actual.getExecutionMessage());
    }

     static Stream<Arguments> stepMapperArgsSource() {
        return Stream.of(
                Arguments.of(
                        new StepResult(
                                new Step("action1", "value1"),
                                "message", ExecutionStatus.SUCCESS
                        ),
                        new StepResult(
                                new Step("action1", "value1"),
                                null, ExecutionStatus.FAIL
                        )
                )
        );
     }
}
