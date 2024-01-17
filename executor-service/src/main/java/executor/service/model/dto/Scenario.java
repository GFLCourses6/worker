package executor.service.model.dto;

import java.util.List;
import java.util.Objects;

public class Scenario {
  private String name;
  private String site;
  private String username;
  private List<Step> steps;

  public Scenario() {
  }

  public Scenario(String name, String site, List<Step> steps) {
    this.name = name;
    this.site = site;
    this.steps = steps;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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
    Scenario scenario = (Scenario) o;
    return Objects.equals(name, scenario.name)
            && Objects.equals(site, scenario.site)
            && Objects.equals(steps, scenario.steps)
            && Objects.equals(username, scenario.username);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, site, steps, username);
  }

  @Override
  public String toString() {
    return "Scenario{name='%s', site='%s', steps=%s, username='%s'}".formatted(
            name,
            site,
            steps,
            username);
  }
}
