package sptech.school.projetoPI.refactor.core.gateway;

import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;

import java.util.List;
import java.util.Optional;

public interface UserGateway {
  Boolean existsById(Integer id);
  Optional<UserDomain> findById(Integer id);
  Optional<UserDomain> save(UserDomain userDomain);
  void deleteById(Integer id);
}
