package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.PaymentTypeRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeService extends AbstractService<PaymentType> {
    private final PaymentTypeRepository repository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public PaymentType postMethod(PaymentType paymentType) {
        if(repository.existsByNameIgnoreCase(paymentType.getName())) {
            throw new EntityConflictException(
                 "O tipo de pagamento '%s' já existe na base de dados".formatted(paymentType.getName())
            );
        }

        paymentType.setId(null);
        paymentType.setCreatedAt(LocalDateTime.now());
        paymentType.setUpdatedAt(LocalDateTime.now());
        return repository.save(paymentType);
    }

    @Override
    public List<PaymentType> getAllMethod() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public PaymentType getByIdMethod(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O pagamento com o ID %d não foi encontrado".formatted(id)
                )
        );
    }

    @Override
    public PaymentType putByIdMethod(PaymentType paymentType, Integer id) {
        if(!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrada".formatted(id)
            );
        }

        if(repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O tipo de pagamento com o ID %d está inativo".formatted(id)
            );
        }

        if(repository.existsByIdNotAndNameIgnoreCase(id, paymentType.getName())) {
            throw new EntityConflictException(
                    "O tipo de pagamento '%s' já existe na base de dados".formatted(paymentType.getName())
            );
        }

        paymentType.setId(id);
        paymentType.setCreatedAt(repository.findById(id).get().getCreatedAt());
        paymentType.setUpdatedAt(LocalDateTime.now());
        return repository.save(paymentType);
    }

    @Override
    public void deleteByIdMethod(Integer id) {
        if(!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O tipo de pagamento com o ID %d já está inativo".formatted(id)
            );
        }

        if(scheduleRepository.existsByPaymentTypeId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes agendamentos estão relacionados com este tipo de pagamento: %s".formatted(scheduleRepository.findAllByPaymentTypeId(id)
                            .stream().map(Schedule::getId).toList())
            );
        }

        PaymentType paymentType = repository.findById(id).get();
        paymentType.setActive(false);
        paymentType.setUpdatedAt(LocalDateTime.now());
        repository.save(paymentType);
    }
}
