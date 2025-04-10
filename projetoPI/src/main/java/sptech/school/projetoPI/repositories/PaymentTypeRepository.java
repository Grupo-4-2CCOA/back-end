package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.PaymentType;

public interface PaymentTypeRepository extends JpaRepository<PaymentType, Integer> {
}
