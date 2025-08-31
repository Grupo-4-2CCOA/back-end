package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleItemService extends AbstractService<ScheduleItem> {
    private final ScheduleItemRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public ScheduleItem postMethod(ScheduleItem scheduleItem) {
        validateRequestBody(scheduleItem);
        scheduleItem.setId(null);
        scheduleItem.setCreatedAt(LocalDateTime.now());
        scheduleItem.setUpdatedAt(LocalDateTime.now());
        return repository.save(scheduleItem);
    }

    @Override
    public List<ScheduleItem> getAllMethod() {
        return repository.findAll();
    }

    @Override
    public ScheduleItem getByIdMethod(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O item de agendamento com o ID %d não foi encontrado".formatted(id)
                )
        );
    }

    @Override
    public ScheduleItem putByIdMethod(ScheduleItem scheduleItem, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O item de agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        validateRequestBody(scheduleItem);
        scheduleItem.setId(id);
        scheduleItem.setCreatedAt(repository.findById(id).get().getCreatedAt());
        scheduleItem.setUpdatedAt(LocalDateTime.now());
        return repository.save(scheduleItem);
    }

    @Override
    public void deleteByIdMethod(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O item de agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        repository.deleteById(id);
    }

    private void validateRequestBody(ScheduleItem scheduleItem) {
        if(!scheduleRepository.existsById(scheduleItem.getSchedule().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(scheduleItem.getSchedule().getId())
            );
        }

        if(!serviceRepository.existsById(scheduleItem.getService().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O serviço com o ID %d não foi encontrado".formatted(scheduleItem.getService().getId())
            );
        }
    }
}
