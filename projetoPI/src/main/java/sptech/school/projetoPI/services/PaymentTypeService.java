package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.PaymentType;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.PaymentTypeRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentTypeService {
    private final PaymentTypeRepository repository;
    private final ScheduleRepository scheduleRepository;

    public PaymentType signPaymentType(PaymentType paymentType) {
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

    public List<PaymentType> getAllPaymentTypes() {
        return repository.findAllByActiveTrue();
    }

    public PaymentType getPaymentTypeById(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O pagamento com o ID %d não foi encontrado".formatted(id)
                )
        );
    }

    public PaymentType updatePaymentTypeById(PaymentType paymentType, Integer id) {
        if(!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrada".formatted(id)
            );
        }

        if(repository.existsByIdNotAndNameIgnoreCase(id, paymentType.getName())) {
            throw new EntityConflictException(
                    "O tipo de pagamento '%s' já existe na base de dados".formatted(paymentType.getName())
            );
        }

        if(repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O tipo de pagamento com o ID %d está inativo".formatted(id)
            );
        }

        paymentType.setId(id);
        paymentType.setCreatedAt(repository.findById(id).get().getCreatedAt());
        paymentType.setUpdatedAt(LocalDateTime.now());
        return repository.save(paymentType);
    }

    public void deletePaymentTypeById(Integer id) {
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
