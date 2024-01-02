package executor.service.controller;

import executor.service.model.dto.ScenarioResultResponse;
import executor.service.service.scenario.result.ScenarioResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ScenarioResultControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScenarioResultService scenarioResultService;

    void getAllScenarioResults()
            throws Exception {
        List<ScenarioResultResponse> scenarioResults =
                Arrays.asList(new ScenarioResultResponse(), new ScenarioResultResponse());
        when(scenarioResultService.getAllScenarioResults()).thenReturn(scenarioResults);
        mockMvc.perform(get("/result").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.length()").value(scenarioResults.size()));
    }

    void getAllScenarioResultsEmptyList()
            throws Exception {
        when(scenarioResultService.getAllScenarioResults()).thenReturn(List.of());
        mockMvc.perform(get("/result").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNoContent());
    }
}