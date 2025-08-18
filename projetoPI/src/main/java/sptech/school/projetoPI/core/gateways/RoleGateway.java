package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.Role;

import java.util.List;
import java.util.Optional;

public interface RoleGateway {
    Role save(Role client);
    boolean existsByName(String name);
    boolean existsById(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    boolean existsByRoleId(Integer id);
    Optional<Role> findByIdAndActiveTrue(Integer id);
    Optional<Role> findById(Integer id);
    Optional<Role> findAllByRoleId(Integer id);
    List<Role> findAllByActiveTrue();
}
