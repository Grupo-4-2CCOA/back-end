package sptech.school.projetoPI.old.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.old.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.old.core.gateways.ScheduleItemGateway;
import sptech.school.projetoPI.old.infrastructure.mappers.ScheduleItemMapper;
import sptech.school.projetoPI.old.infrastructure.persistence.entity.ScheduleItemJpaEntity;
import sptech.school.projetoPI.old.infrastructure.persistence.repositories.JpaScheduleItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleItemAdapter implements ScheduleItemGateway {

    private final JpaScheduleItemRepository repository;

    @Override
    public ScheduleItemDomain save(ScheduleItemDomain scheduleItemDomain) {
        ScheduleItemJpaEntity jpaEntity = ScheduleItemMapper.toJpaEntity(scheduleItemDomain);
        ScheduleItemJpaEntity savedEntity = repository.save(jpaEntity);
        return ScheduleItemMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<ScheduleItemDomain> findById(Integer id) {
        return repository.findById(id).map(ScheduleItemMapper::toDomain);
    }

    @Override
    public List<ScheduleItemDomain> findAll() {
        return repository.findAll().stream()
                .map(ScheduleItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public ScheduleItemDomain deleteById(Integer id) {
        Optional<ScheduleItemDomain> scheduleItemOpt = this.findById(id);
        scheduleItemOpt.ifPresent(a -> repository.deleteById(id));
        return scheduleItemOpt.orElse(null);
    }

    @Override
    public List<ScheduleItemDomain> findAllBySchedule_Id(Integer id) {
        var scheduleItemTestedoTesteMaldito2 = repository.findAll();
        var scheduleItemTestedoTesteMaldito = repository.findAllBySchedule_Id(id);
        return scheduleItemTestedoTesteMaldito.stream()
                .map(ScheduleItemMapper::toDomain)
                .collect(Collectors.toList());
    }
}
