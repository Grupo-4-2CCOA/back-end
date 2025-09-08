package sptech.school.projetoPI.infrastructure.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;
import sptech.school.projetoPI.infrastructure.repositories.JpaScheduleItemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleItemRepositoryImpl implements ScheduleItemGateway {

    private final JpaScheduleItemRepository repository;

    @Override
    public ScheduleItem save(ScheduleItem scheduleItem) {
        return null;
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public Optional<ScheduleItem> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<ScheduleItem> findAll() {
        return List.of();
    }

    @Override
    public ScheduleItem deleteById(Integer id) {
        return null;
    }
}
