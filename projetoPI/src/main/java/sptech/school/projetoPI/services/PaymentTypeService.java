package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.PaymentType;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.repositories.PaymentTypeRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;

import java.util.List;

@Service
public class PaymentTypeService {

    private final PaymentTypeRepository repository;
    private final ScheduleRepository scheduleRepository;

    public PaymentTypeService(PaymentTypeRepository repository, ScheduleRepository scheduleRepository) {
        this.repository = repository;
        this.scheduleRepository = scheduleRepository;
    }

    public PaymentType signPaymentType(PaymentType paymentType) {
        paymentType.setId(null);
        return repository.save(paymentType);
    }

    public List<PaymentType> getAllPaymentsType() {
        return repository.findAll();
    }

    public PaymentType getPaymentTypeById(Integer id) {
        return repository.findById(id).orElseThrow(
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

        paymentType.setId(id);
        return repository.save(paymentType);
    }

    public ResponseEntity<Void> deletePaymentTypeById(Integer id) {
        if(!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O tipo de pagamento com o ID %d não foi encontrado".formatted(id)
            );
        }

        if(scheduleRepository.existsByPaymentTypeId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes agendamentos estão relacionados com este tipo de pagamento: %s".formatted(scheduleRepository.findAllByPaymentTypeId(id)
                            .stream().map(Schedule::getId).toList())
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
