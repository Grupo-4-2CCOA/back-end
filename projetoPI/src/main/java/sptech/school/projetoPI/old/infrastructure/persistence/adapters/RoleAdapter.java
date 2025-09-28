package sptech.school.projetoPI.old.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.old.core.domains.RoleDomain;
import sptech.school.projetoPI.old.core.gateways.RoleGateway;
import sptech.school.projetoPI.old.infrastructure.mappers.RoleMapper;
import sptech.school.projetoPI.old.infrastructure.persistence.entity.RoleJpaEntity;
import sptech.school.projetoPI.old.infrastructure.persistence.repositories.JpaRoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleAdapter implements RoleGateway {

    private final JpaRoleRepository repository;

    @Override
    public RoleDomain save(RoleDomain client) {
        RoleJpaEntity jpaEntity= RoleMapper.toJpaEntity(client);
        RoleJpaEntity savedEntity = repository.save(jpaEntity);
        return RoleMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByIdAndActiveFalse(Integer id) {
        return repository.existsByIdAndActiveFalse(id);
    }

    @Override
    public boolean existsByIdNotAndName(Integer id, String name) {
        return existsByIdNotAndName(id, name);
    }

    @Override
    public Optional<RoleDomain> findByIdAndActiveTrue(Integer id) {
        return repository.findByIdAndActiveTrue(id).map(RoleMapper::toDomain);
    }

    @Override
    public Optional<RoleDomain> findById(Integer id) {
        return repository.findById(id).map(RoleMapper::toDomain);
    }

    @Override
    public List<RoleDomain> findAll() {
        return repository.findAll().stream()
                .map(RoleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDomain> findAllByActiveTrue() {
        return repository.findAllByActiveTrue().stream()
                .map(RoleMapper::toDomain)
                .collect(Collectors.toList());
    }
}
