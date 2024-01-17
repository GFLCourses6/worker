package executor.service.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

public class Scenario {
  private String name;
  private String site;
  private String username;
  private List<Step> steps;

  public Scenario() {
  }

  public Scenario(String name, String site, String username, List<Step> steps) {
    this.name = name;
    this.site = site;
    this.username = username;
    this.steps = steps;
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

  public void setUsername(String username) {
    this.username = username;
  }

  public List<Step> getSteps() {
    return steps;
  }

  public void setSteps(List<Step> steps) {
    this.steps = steps;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Scenario scenarioDTO = (Scenario) o;
    return Objects.equals(name, scenarioDTO.name) &&
        Objects.equals(site, scenarioDTO.site) &&
        Objects.equals(steps, scenarioDTO.steps);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, site, steps);
  }

  @Override
  public String toString() {
    return "Scenario{" +
        "name='" + name + '\'' +
        ", site='" + site + '\'' +
        ", steps=" + steps +
        '}';
  }
}

