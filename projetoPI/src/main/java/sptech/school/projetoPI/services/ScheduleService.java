package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.repositories.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService extends AbstractService<Schedule> {
    private final ScheduleRepository repository;
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;
    private final PaymentTypeRepository paymentTypeRepository;

    @Override
    public Schedule postMethod(Schedule schedule) {
        if (repository.existsByAppointmentDatetime(schedule.getAppointmentDatetime())) {
            throw new EntityConflictException(
                    "Um atendimento para este horário neste dia já existe"
            );
        }

        validateRequestBody(schedule);
        schedule.setId(null);
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());
        return repository.save(schedule);
    }

    @Override
    public List<Schedule> getAllMethod() {
        return repository.findAll();
    }

    @Override
    public Schedule getByIdMethod(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O agendamento com o ID %d não foi encontrado".formatted(id)
                )
        );
    }

    @Override
    public Schedule putByIdMethod(Schedule schedule, Integer id) {
        if(!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (repository.existsByIdNotAndAppointmentDatetime(id, schedule.getAppointmentDatetime())) {
            throw new EntityConflictException(
                    "Um atendimento para este horário neste dia já existe"
            );
        }

        validateRequestBody(schedule);
        schedule.setId(id);
        schedule.setCreatedAt(repository.findById(id).get().getCreatedAt());
        return repository.save(schedule);
    }

    @Override
    public void deleteByIdMethod(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (repository.existsByIdAndStatus(id, Status.CANCELED)) {
            throw new InactiveEntityException(
                    "O agendamento com o ID %d já foi cancelado".formatted(id)
            );
        }

        Schedule schedule = repository.findById(id).get();
        schedule.setStatus(Status.CANCELED);
        schedule.setUpdatedAt(LocalDateTime.now());
        repository.save(schedule);
    }

    private void validateRequestBody(Schedule schedule) {
        if (!clientRepository.existsByIdAndActiveTrue(schedule.getClient().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O cliente com o ID %d não foi encontrado".formatted(schedule.getClient().getId())
            );
        }

        if (!employeeRepository.existsByIdAndActiveTrue(schedule.getEmployee().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O funcionário com o ID %d não foi encontrado".formatted(schedule.getEmployee().getId())
            );
        }

        if (schedule.getPaymentType() != null && !paymentTypeRepository.existsByIdAndActiveTrue(schedule.getPaymentType().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrado".formatted(schedule.getPaymentType().getId())
            );
        }

        // TODO: Adicionar um if que checa se a duração do atendimento não conflita com outro marcado para o mesmo dia e que joga a TimeConflictException
    }
}
