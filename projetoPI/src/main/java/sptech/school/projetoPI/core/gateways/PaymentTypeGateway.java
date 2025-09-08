package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.PaymentTypeDomain;

import java.util.List;
import java.util.Optional;

public interface PaymentTypeGateway {
    PaymentTypeDomain save(PaymentTypeDomain paymentTypeDomain);
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByIdNotAndNameIgnoreCase(Integer id, String name);
    boolean existsById(Integer id);
    Optional<PaymentTypeDomain> findById(Integer id);
    Optional<PaymentTypeDomain> findByIdAndActiveTrue(Integer id);
    List<PaymentTypeDomain> findAllByActiveTrue();
}
