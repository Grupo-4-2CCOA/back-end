package sptech.school.projetoPI.infrastructure.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;
import sptech.school.projetoPI.infrastructure.persistence.ScheduleItemJpaEntity;
import sptech.school.projetoPI.infrastructure.repositories.JpaScheduleItemRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScheduleItemRepositoryImpl implements ScheduleItemGateway {

    private final JpaScheduleItemRepository repository;

    @Override
    public ScheduleItem save(ScheduleItem scheduleItem) {
        return repository.save(scheduleItem);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<ScheduleItemJpaEntity> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<ScheduleItemJpaEntity> findAll() {
        return repository.findAll();
    }
}
