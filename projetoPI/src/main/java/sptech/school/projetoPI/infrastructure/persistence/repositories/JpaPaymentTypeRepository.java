package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.infrastructure.persistence.entity.PaymentTypeJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaPaymentTypeRepository extends JpaRepository<PaymentTypeJpaEntity, Integer> {
    boolean existsByIdAndIsActiveTrue(Integer id);
    boolean existsByIdAndIsActiveFalse(Integer id);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByIdNotAndNameIgnoreCase(Integer id, String name);
    Optional<PaymentTypeJpaEntity> findByIdAndIsActiveTrue(Integer id);
    List<PaymentTypeJpaEntity> findAllByIsActiveTrue();
}
