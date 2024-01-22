package executor.service.model.entity;

import executor.service.model.dto.ProxyConfigHolder;
import executor.service.model.dto.Scenario;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "scenario_result")
@EntityListeners(AuditingEntityListener.class)
public class ScenarioResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scenario_result_seq")
    @SequenceGenerator(name = "scenario_result_seq", sequenceName = "scenario_result_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "site", nullable = false)
    private String site;

    @Column(name = "username")
    private String username;

    @Column(name = "proxy")
    private String proxy;

    @OneToMany(mappedBy = "scenarioResult", cascade = CascadeType.ALL)
    private List<StepResult> stepResults = new ArrayList<>();

    public ScenarioResult(Scenario scenario) {
        this.name = scenario.getName();
        this.site = scenario.getSite();
        this.username = scenario.getUsername();
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

    public String getUsername() {
        return username;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(ProxyConfigHolder proxy) {
        if (proxy != null && proxy.getProxyNetworkConfig() != null) {
            var network = proxy.getProxyNetworkConfig();
            this.proxy = String.format("%s:%d", network.getHostname(), network.getPort());
        }
    }

    public List<StepResult> getStepResults() {
        return stepResults;
    }

    @Override
    public String toString() {
        return "ScenarioResult{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", site='" + site + '\''
                + ", createdBy='" + username + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScenarioResult that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(site, that.site)
                && Objects.equals(username, that.username)
                && Objects.equals(stepResults, that.stepResults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, site, username, stepResults);
    }
}
