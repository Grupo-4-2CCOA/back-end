package sptech.school.projetoPI.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.ServiceGateway;
import sptech.school.projetoPI.infrastructure.mappers.ServiceMapper;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaServiceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceAdapter implements ServiceGateway {

    private final JpaServiceRepository jpaRepository;

    @Override
    public ServiceDomain save(ServiceDomain serviceDomain) {
        var entity = ServiceMapper.toJpaEntity(serviceDomain);
        var saved = jpaRepository.save(entity);
        return ServiceMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(Integer id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<ServiceDomain> findAll() {
        return jpaRepository.findAll().stream()
                .map(ServiceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ServiceDomain> findById(Integer id) {
        return jpaRepository.findById(id).map(ServiceMapper::toDomain);
    }

    @Override
    @CacheEvict(value = "services", allEntries = true)
    public ServiceDomain deleteById(Integer id) {
        Optional<ServiceDomain> serviceOpt = this.findById(id);
        serviceOpt.ifPresent(a -> jpaRepository.deleteById(id));
        return serviceOpt.orElse(null);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByName(name);
    }

    @Override
    public boolean existsByCategoryId(Integer id) {
        return jpaRepository.existsByCategoryId(id);
    }

    @Override
    public boolean existsByIdAndActiveFalse(Integer id) {
        return jpaRepository.existsByIdAndIsActiveFalse(id);
    }

    @Override
    public boolean existsByIdNotAndName(Integer id, String name) {
        return jpaRepository.existsByIdNotAndName(id, name);
    }

    @Override
    public Optional<ServiceDomain> findByIdAndActiveTrue(Integer id) {
        return jpaRepository.findByIdAndIsActiveTrue(id).map(ServiceMapper::toDomain);
    }

    @Override
    public List<ServiceDomain> findAllByCategoryId(Integer categoryId) {
        return jpaRepository.findAllByCategoryId(categoryId).stream()
                .map(ServiceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "services", key = "'all'")
    public List<ServiceDomain> findAllByActiveTrue() {
        return jpaRepository.findAllByIsActiveTrue().stream()
                .map(ServiceMapper::toDomain)
                .collect(Collectors.toList());
    }
}
