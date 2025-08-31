package sptech.school.projetoPI.infrastructure.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;
import sptech.school.projetoPI.infrastructure.mappers.PaymentTypeMapper;
import sptech.school.projetoPI.infrastructure.persistence.PaymentTypeJpaEntity;
import sptech.school.projetoPI.infrastructure.persistence.PaymentTypeJpaRepository;
import sptech.school.projetoPI.infrastructure.repositories.JpaPaymentTypeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PaymentTypeRepositoryImpl implements PaymentTypeGateway {

    private final JpaPaymentTypeRepository repository;

    @Override
    public PaymentType save(PaymentType paymentType) {
        PaymentTypeJpaEntity jpaEntity = PaymentTypeMapper.toJpaEntity(paymentType);
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
    public Optional<PaymentType> findById(Integer id) {
        return repository.findById(id).map(PaymentTypeMapper::toDomain);
    }

    @Override
    public Optional<PaymentType> findByIdAndActiveTrue(Integer id) {
        return repository.findByIdAndActiveTrue(id).map(PaymentTypeMapper::toDomain);
    }

    @Override
    public List<PaymentType> findAllByActiveTrue() {
        return repository.findAllByActiveTrue().stream()
                .map(PaymentTypeMapper::toDomain)
                .collect(Collectors.toList());
    }
}