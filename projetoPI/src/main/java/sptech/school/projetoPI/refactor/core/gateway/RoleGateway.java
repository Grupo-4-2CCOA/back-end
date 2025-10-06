package sptech.school.projetoPI.refactor.core.gateway;

import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;

import java.util.Optional;

public interface RoleGateway {
  Boolean existsById(Integer id);
  Optional<RoleDomain> findById(Integer id);
  RoleDomain save(RoleDomain roleDomain);
  void deleteById(Integer id);
  Boolean existsByName(String name);
  Optional<RoleDomain> findByName(String name);
}
