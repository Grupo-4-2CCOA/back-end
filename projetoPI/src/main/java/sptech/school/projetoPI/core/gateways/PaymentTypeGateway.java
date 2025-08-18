package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.PaymentType;

import java.util.List;
import java.util.Optional;

public interface PaymentTypeGateway {
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByNameIgnoreCase(String name);
    boolean existsByIdNotAndNameIgnoreCase(Integer id, String name);
    Optional<PaymentType> findByIdAndActiveTrue(Integer id);
    List<PaymentType> findAllByActiveTrue();
}
