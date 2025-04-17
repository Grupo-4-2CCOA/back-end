package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Role;
import sptech.school.projetoPI.entities.User;
import sptech.school.projetoPI.exceptions.exceptionClass.*;
import sptech.school.projetoPI.repositories.RoleRepository;
import sptech.school.projetoPI.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;
    private final UserRepository userRepository;

    public Role signRole(Role role) {
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

    public List<Role> getAllRoles() {
        return repository.findAllByActiveTrue();
    }

    public Role getRoleById(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O cargo com o ID %d não foi encontrada".formatted(id)
                )
        );
    }

    public Role updateRoleById(Role role, Integer id) {
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

    public void deleteRoleById(Integer id) {
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

        if (userRepository.existsByRoleId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes usuários estão relacionados com este cargo: %s".formatted(userRepository.findAllByRoleId(id)
                            .stream().map(User::getId).toList())
            );
        }

        Role role = repository.findById(id).get();
        role.setActive(false);
        role.setUpdatedAt(LocalDateTime.now());
        repository.save(role);
    }
}
