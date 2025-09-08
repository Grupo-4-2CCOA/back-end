package sptech.school.projetoPI.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domain.ScheduleItemDomain;
import sptech.school.projetoPI.core.gateway.ScheduleItemGateway;
import sptech.school.projetoPI.infrastructure.persistence.repository.JpaScheduleItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleItemRepositoryAdapter implements ScheduleItemGateway {

    private final JpaScheduleItemRepository repository;

    @Override
    public ScheduleItemDomain save(ScheduleItemDomain scheduleItemDomain) {
        return null;
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public Optional<ScheduleItemDomain> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<ScheduleItemDomain> findAll() {
        return List.of();
    }

    @Override
    public ScheduleItemDomain deleteById(Integer id) {
        return null;
    }
}
