package sptech.school.projetoPI.old.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.old.infrastructure.persistence.entity.PaymentTypeJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaPaymentTypeRepository extends JpaRepository<PaymentTypeJpaEntity, Integer> {
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByIdNotAndNameIgnoreCase(Integer id, String name);
    Optional<PaymentTypeJpaEntity> findByIdAndActiveTrue(Integer id);
    List<PaymentTypeJpaEntity> findAllByActiveTrue();
}
