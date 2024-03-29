package executor.service.dao;

import executor.service.model.entity.ScenarioResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioResultRepository
        extends JpaRepository<ScenarioResult, Long> {

    @Query("SELECT DISTINCT s FROM ScenarioResult s LEFT JOIN FETCH s.stepResults s2 WHERE s.username = :username " +
            "ORDER BY s.id, s2.id")
    List<ScenarioResult> findAllFetchStepResults(String username);
}
