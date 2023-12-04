package executor.service.model;

import org.apache.logging.log4j.message.StringMapMessage;

public class ScenarioLogMessage extends StringMapMessage {

    public ScenarioLogMessage(String scenarioName) {
        addScenarioName(scenarioName);
    }

    public ScenarioLogMessage() {
    }

    public void addScenarioName(String scenarioName) {
        put("scenario_name", scenarioName);
    }

    public void addExecutionMessage(String message) {
        put("message", message);
    }
}
