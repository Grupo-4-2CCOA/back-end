package sptech.school.projetoPI.services;

import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Payment;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.repositories.PaymentRepository;
import sptech.school.projetoPI.repositories.UserRepository;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Payment signPayment(Payment payment) {
        if(!userRepository.existsById(payment.getUser().getId())) {
            throw new EntityNotFoundException(
                    "O usuário com o ID %d não foi encontrado".formatted(payment.getUser().getId())
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
