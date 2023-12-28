package executor.service.model.entity;

import executor.service.model.Scenario;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "scenario_result")
public class ScenarioResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "site", nullable = false)
    private String site;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

    @OneToMany(mappedBy = "scenarioResult",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<StepResult> stepResults = new ArrayList<>();

    public ScenarioResult(Scenario scenario) {
        this.name = scenario.getName();
        this.site = scenario.getSite();
    }

    public ScenarioResult() {
    }

    public void addStepResult(StepResult stepResult) {
        stepResult.setScenarioResult(this);
        stepResults.add(stepResult);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public List<StepResult> getStepResults() {
        return stepResults;
    }
}
