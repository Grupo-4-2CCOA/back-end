package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.PaymentType;

import java.util.List;
import java.util.Optional;

public interface PaymentTypeGateway {
    PaymentType save(PaymentType paymentType);
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByIdNotAndNameIgnoreCase(Integer id, String name);
    boolean existsById(Integer id);
    Optional<PaymentType> findById(Integer id);
    Optional<PaymentType> findByIdAndActiveTrue(Integer id);
    List<PaymentType> findAllByActiveTrue();
}
