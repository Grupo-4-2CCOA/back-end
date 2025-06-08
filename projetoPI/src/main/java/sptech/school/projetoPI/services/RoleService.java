package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Role;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.exceptions.exceptionClass.*;
import sptech.school.projetoPI.repositories.RoleRepository;
import sptech.school.projetoPI.repositories.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService extends AbstractService<Role> {
    private final RoleRepository repository;
    private final EmployeeRepository employeeRepository;

    @Override
    public Role postMethod(Role role) {
        if(repository.existsByName(role.getName())) {
            throw new EntityConflictException(
                    "Cargo com o mesmo nome já existe na base de dados"
            );
        }

        role.setId(null);
        role.setCreatedAt(LocalDateTime.now());
        role.setUpdatedAt(LocalDateTime.now());
        return repository.save(role);
    }

    @Override
    public List<Role> getAllMethod() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public Role getByIdMethod(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O cargo com o ID %d não foi encontrada".formatted(id)
                )
        );
    }

    @Override
    public Role putByIdMethod(Role role, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O cargo com o ID %d não foi encontrada".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O cargo com o ID %d já está inativa".formatted(id)
            );
        }

        if(repository.existsByIdNotAndName(id, role.getName())) {
            throw new EntityConflictException(
                    "Cargo com o mesmo nome já existe na base de dados"
            );
        }

        role.setId(id);
        role.setCreatedAt(repository.findById(id).get().getCreatedAt());
        role.setUpdatedAt(LocalDateTime.now());
        return repository.save(role);
    }

    @Override
    public void deleteByIdMethod(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O cargo com o ID %d não foi encontrada".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O cargo com o ID %d já está inativa".formatted(id)
            );
        }

        if (employeeRepository.existsByRoleId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes usuários estão relacionados com este cargo: %s".formatted(employeeRepository.findAllByRoleId(id)
                            .stream().map(Employee::getId).toList())
            );
        }

        Role role = repository.findById(id).get();
        role.setActive(false);
        role.setUpdatedAt(LocalDateTime.now());
        repository.save(role);
    }
}
