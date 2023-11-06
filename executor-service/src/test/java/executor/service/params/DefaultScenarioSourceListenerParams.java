package executor.service.params;

import executor.service.model.Scenario;
import executor.service.model.Step;
import org.junit.jupiter.params.provider.Arguments;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultScenarioSourceListenerParams {

    static Stream<Arguments> testExecute() {
        Queue<Scenario> scenarios = new LinkedBlockingQueue<>();

        scenarios.add(new Scenario(
                "test scenario 1",
                "http://info.cern.ch",
                Stream.of(
                        new Step(
                                "clickCss",
                                "body > ul > li:nth-child(1) > a"
                        ),
                        new Step(
                                "sleep",
                                "5:10"
                        ),
                        new Step(
                                "clickXpath",
                                "/html/body/p"
                        )
                ).collect(Collectors.toList())
        ));
        scenarios.add(new Scenario(
                "test scenario 2",
                "http://info.cern.ch",
                Stream.of(
                        new Step(
                                "clickXpath",
                                "/html/body/p"
                        ),
                        new Step(
                                "sleep",
                                "5:10"
                        ),
                        new Step(
                                "clickCss",
                                "body > ul > li:nth-child(1) > a"
                        )
                ).collect(Collectors.toList())
        ));
        return Stream.of(
                Arguments.of(scenarios)
        );
    }
}
