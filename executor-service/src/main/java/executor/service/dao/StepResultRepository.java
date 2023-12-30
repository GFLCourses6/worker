package executor.service.dao;

import executor.service.model.entity.StepResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepResultRepository
        extends JpaRepository<StepResult, Long> {
}
