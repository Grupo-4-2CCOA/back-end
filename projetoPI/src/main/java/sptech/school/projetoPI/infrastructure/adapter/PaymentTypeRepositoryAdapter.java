package sptech.school.projetoPI.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domain.PaymentTypeDomain;
import sptech.school.projetoPI.core.gateway.PaymentTypeGateway;
import sptech.school.projetoPI.infrastructure.mapper.PaymentTypeMapper;
import sptech.school.projetoPI.infrastructure.persistence.entity.PaymentTypeJpaEntity;
import sptech.school.projetoPI.infrastructure.persistence.repository.JpaPaymentTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentTypeRepositoryAdapter implements PaymentTypeGateway {

    private final JpaPaymentTypeRepository repository;

    @Override
    public PaymentTypeDomain save(PaymentTypeDomain paymentTypeDomain) {
        PaymentTypeJpaEntity jpaEntity = PaymentTypeMapper.toJpaEntity(paymentTypeDomain);
        PaymentTypeJpaEntity savedEntity = repository.save(jpaEntity);
        return PaymentTypeMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByIdAndActiveTrue(Integer id) {
        return repository.existsByIdAndActiveTrue(id);
    }

    @Override
    public boolean existsByIdAndActiveFalse(Integer id) {
        return repository.existsByIdAndActiveFalse(id);
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
        return repository.findByIdAndActiveTrue(id).map(PaymentTypeMapper::toDomain);
    }

    @Override
    public List<PaymentTypeDomain> findAllByActiveTrue() {
        return repository.findAllByActiveTrue().stream()
                .map(PaymentTypeMapper::toDomain)
                .collect(Collectors.toList());
    }
}