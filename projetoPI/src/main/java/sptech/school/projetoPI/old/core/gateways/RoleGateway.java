package sptech.school.projetoPI.old.core.gateways;

import sptech.school.projetoPI.old.core.domains.RoleDomain;

import java.util.List;
import java.util.Optional;

public interface RoleGateway {
    RoleDomain save(RoleDomain client);
    boolean existsById(Integer id);
    List<RoleDomain> findAll();
    boolean existsByName(String name);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<RoleDomain> findByIdAndActiveTrue(Integer id);
    Optional<RoleDomain> findById(Integer id);
    List<RoleDomain> findAllByActiveTrue();
}
