package sptech.school.projetoPI.infrastructure.services;

import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.core.gateways.RoleGateway;

import java.util.List;
import java.util.Optional;

public class RolePersistenceService implements RoleGateway {
    @Override
    public Role save(Role client) {
        return null;
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }

    @Override
    public boolean existsById(Integer id) {
        return false;
    }

    @Override
    public boolean existsByIdAndActiveFalse(Integer id) {
        return false;
    }

    @Override
    public boolean existsByIdNotAndName(Integer id, String name) {
        return false;
    }

    @Override
    public boolean existsByRoleId(Integer id) {
        return false;
    }

    @Override
    public Optional<Role> findByIdAndActiveTrue(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Optional<Role> findAllByRoleId(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<Role> findAllByActiveTrue() {
        return List.of();
    }
}
