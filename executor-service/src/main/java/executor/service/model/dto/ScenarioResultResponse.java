package executor.service.model.dto;

import java.util.List;
import java.util.Objects;

public class ScenarioResultResponse {

    private Long id;
    private String name;
    private String site;
    private String createdBy;
    private List<StepResultResponse> stepResults;

    public ScenarioResultResponse(Long id, String name, String site,
                                  String createdBy, List<StepResultResponse> stepResults) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.createdBy = createdBy;
        this.stepResults = stepResults;
    }

    public ScenarioResultResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<StepResultResponse> getStepResults() {
        return stepResults;
    }

    public void setStepResults(List<StepResultResponse> stepResults) {
        this.stepResults = stepResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScenarioResultResponse that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(site, that.site)
                && Objects.equals(createdBy, that.createdBy)
                && Objects.equals(stepResults, that.stepResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, site, createdBy, stepResults);
    }

    @Override
    public String toString() {
        return "ScenarioResultResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", stepResults=" + stepResults +
                '}';
    }
}
