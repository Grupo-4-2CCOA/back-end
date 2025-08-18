package sptech.school.projetoPI.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.infrastructure.persistence.PaymentTypeJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaPaymentTypeRepository extends JpaRepository<PaymentTypeJpaEntity, Integer> {
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByIdNotAndNameIgnoreCase(Integer id, String name);
    Optional<PaymentType> findByIdAndActiveTrue(Integer id);
    List<PaymentType> findAllByActiveTrue();
}
