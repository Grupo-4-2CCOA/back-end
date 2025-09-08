package sptech.school.projetoPI.infrastructure.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.infrastructure.mappers.RoleMapper;
import sptech.school.projetoPI.infrastructure.persistence.RoleJpaEntity;
import sptech.school.projetoPI.infrastructure.repositories.JpaRoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleGateway {

    private final JpaRoleRepository repository;

    @Override
    public Role save(Role client) {
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
    public Optional<Role> findByIdAndActiveTrue(Integer id) {
        return repository.findByIdAndActiveTrue(id).map(RoleMapper::toDomain);
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return repository.findById(id).map(RoleMapper::toDomain);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll().stream()
                .map(RoleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Role> findAllByActiveTrue() {
        return repository.findAllByActiveTrue().stream()
                .map(RoleMapper::toDomain)
                .collect(Collectors.toList());
    }
}
