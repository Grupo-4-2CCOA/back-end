package sptech.school.projetoPI.old.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.old.core.domains.EmployeeDomain;
import sptech.school.projetoPI.old.core.gateways.EmployeeGateway;
import sptech.school.projetoPI.old.infrastructure.mappers.EmployeeMapper;
import sptech.school.projetoPI.old.infrastructure.persistence.entity.EmployeeJpaEntity;
import sptech.school.projetoPI.old.infrastructure.persistence.repositories.JpaEmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeAdapter implements EmployeeGateway {

    private final JpaEmployeeRepository repository;

    @Override
    public EmployeeDomain save(EmployeeDomain employeeDomain) {
        EmployeeJpaEntity jpaEntity = EmployeeMapper.toJpaEntity(employeeDomain);
        EmployeeJpaEntity savedEntity = repository.save(jpaEntity);
        return EmployeeMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return repository.existsByCpf(cpf);
    }

    @Override
    public boolean existsByIdNotAndCpf(Integer id, String cpf) {
        return repository.existsByIdNotAndCpf(id, cpf);
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return repository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email) {
        return repository.existsByIdNotAndEmailIgnoreCase(id, email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
    }

    @Override
    public boolean existsByIdNotAndPhone(Integer id, String phone) {
        return repository.existsByIdNotAndPhone(id, phone);
    }

    @Override
    public boolean existsByRoleId(Integer id) {
        return repository.existsByRoleId(id);
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
    public boolean existsByRoleName(String roleName) {
        return repository.existsByRoleName(roleName);
    }

    @Override
    public boolean existsByIdNotAndRoleName(Integer id, String roleName) {
        return repository.existsByIdNotAndRoleName(id, roleName);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public Optional<EmployeeDomain> findByIdAndActiveTrue(Integer id) {
        return repository.findByIdAndActiveTrue(id).map(EmployeeMapper::toDomain);
    }

    @Override
    public List<EmployeeDomain> findAllByActiveTrue() {
        return repository.findAllByActiveTrue().stream()
                .map(EmployeeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeDomain> findById(Integer id) {
        return repository.findById(id).map(EmployeeMapper::toDomain);
    }

    @Override
    public List<EmployeeDomain> findAllByRoleId(Integer id) {
        return repository.findAllByRoleId(id).stream()
                .map(EmployeeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeDomain> findByEmail(String email) {
        return repository.findByEmail(email).map(EmployeeMapper::toDomain);
    }
}