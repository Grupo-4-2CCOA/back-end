package sptech.school.projetoPI.refactor.core.gateway;

import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;

import java.util.List;
import java.util.Optional;

public interface UserGateway {
  Boolean existsById(Integer id);
  Optional<UserDomain> findById(Integer id);
  void save(UserDomain userDomain);
  void deleteById(Integer id);
  Boolean existsByRoleDomain_Id(Integer id);
}
