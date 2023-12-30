package executor.service.dao;

import executor.service.model.entity.ScenarioResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioResultRepository
        extends JpaRepository<ScenarioResult, Long> {

    @NonNull
    @Override
    Page<ScenarioResult> findAll(@NonNull Pageable pageable);

    @NonNull
    @Override
    <S extends ScenarioResult> S save(@NonNull S scenarioResult);

    @NonNull
    @Override
    <S extends ScenarioResult> List<S> saveAll(@NonNull Iterable<S> entities);
}
