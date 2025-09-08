package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.Role;

import java.util.List;
import java.util.Optional;

public interface RoleGateway {
    Role save(Role client);
    boolean existsById(Integer id);
    List<Role> findAll();
    boolean existsByName(String name);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<Role> findByIdAndActiveTrue(Integer id);
    Optional<Role> findById(Integer id);
    List<Role> findAllByActiveTrue();
}
