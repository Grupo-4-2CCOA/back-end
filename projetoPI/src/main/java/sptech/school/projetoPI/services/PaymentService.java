package sptech.school.projetoPI.services;

import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Payment;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.repositories.AppointmentRepository;
import sptech.school.projetoPI.repositories.PaymentRepository;
import sptech.school.projetoPI.repositories.UserRepository;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final AppointmentRepository appointmentRepository;

    public PaymentService(PaymentRepository repository, AppointmentRepository appointmentRepository) {
        this.repository = repository;
        this.appointmentRepository = appointmentRepository;
    }

    public Payment signPayment(Payment payment) {
        if(!appointmentRepository.existsById(payment.getAppointment().getId())) {
            throw new EntityNotFoundException(
                    "O atendimento com o ID %d não foi encontrado".formatted(payment.getAppointment().getId())
            );
        }

        payment.setId(null);
        return repository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Payment getPaymentById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O pagamento com o ID %d não foi encontrado".formatted(id)
                )
        );
    }
}
