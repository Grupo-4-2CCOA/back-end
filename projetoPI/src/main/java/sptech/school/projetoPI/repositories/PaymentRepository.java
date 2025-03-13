package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
