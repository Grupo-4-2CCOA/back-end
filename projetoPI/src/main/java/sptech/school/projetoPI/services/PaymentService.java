package sptech.school.projetoPI.services;

import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Payment;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.repositories.PaymentRepository;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment signPayment(Payment payment) {
        return repository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

    public Payment getPaymentById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O pagamento com o ID %d não foi encontrado.".formatted(id)
                )
        );
    }
}
