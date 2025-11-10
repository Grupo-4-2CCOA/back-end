package sptech.school.projetoPI.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.PaymentTypeDomain;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;
import sptech.school.projetoPI.infrastructure.mappers.PaymentTypeMapper;
import sptech.school.projetoPI.infrastructure.persistence.entity.PaymentTypeJpaEntity;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaPaymentTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentTypeAdapter implements PaymentTypeGateway {

    private final JpaPaymentTypeRepository repository;

    @Override
    public PaymentTypeDomain save(PaymentTypeDomain paymentTypeDomain) {
        PaymentTypeJpaEntity jpaEntity = PaymentTypeMapper.toJpaEntity(paymentTypeDomain);
        PaymentTypeJpaEntity savedEntity = repository.save(jpaEntity);
        return PaymentTypeMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByIdAndActiveTrue(Integer id) {
        return repository.existsByIdAndIsActiveTrue(id);
    }

    @Override
    public boolean existsByIdAndActiveFalse(Integer id) {
        return repository.existsByIdAndIsActiveFalse(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return repository.existsByNameIgnoreCase(name);
    }

    @Override
    public boolean existsByIdNotAndNameIgnoreCase(Integer id, String name) {
        return repository.existsByIdNotAndNameIgnoreCase(id, name);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<PaymentTypeDomain> findById(Integer id) {
        return repository.findById(id).map(PaymentTypeMapper::toDomain);
    }

    @Override
    public Optional<PaymentTypeDomain> findByIdAndActiveTrue(Integer id) {
        return repository.findByIdAndIsActiveTrue(id).map(PaymentTypeMapper::toDomain);
    }

    @Override
    public List<PaymentTypeDomain> findAllByActiveTrue() {
        return repository.findAllByIsActiveTrue().stream()
                .map(PaymentTypeMapper::toDomain)
                .collect(Collectors.toList());
    }
}