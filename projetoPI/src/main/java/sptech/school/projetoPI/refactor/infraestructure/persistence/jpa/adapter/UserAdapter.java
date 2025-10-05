package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;
import sptech.school.projetoPI.refactor.core.gateway.UserGateway;
import sptech.school.projetoPI.refactor.infraestructure.mapper.UserMapper;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.UserJpaEntity;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.repository.UserRepository;

import java.util.Optional;

// TODO: adicionar lógica de soft-delete (com verificação de isActive nos exists) nos UseCases;
@Service
@RequiredArgsConstructor
public class UserAdapter implements UserGateway {
  private final UserRepository userRepository;

  @Override
  public Boolean existsById(Integer id) {
    return userRepository.existsById(id);
  }

  @Override
  public Optional<UserDomain> findById(Integer id) {
    UserJpaEntity userJpaEntity = userRepository.findById(id).orElse(null);
    UserDomain userDomain = UserMapper.toUserDomain(userJpaEntity);
    return Optional.ofNullable(userDomain);
  }

  @Override
  public void save(UserDomain userDomain) {
    UserJpaEntity userJpaEntity = UserMapper.toUserJpaEntity(userDomain);
    userRepository.save(userJpaEntity);
  }

  @Override
  public void updateById(Integer id, UserDomain userDomain) {
    UserJpaEntity userJpaEntity = UserMapper.toUserJpaEntity(userDomain);
    userJpaEntity.setId(id);
    userRepository.save(userJpaEntity);
  }

  @Override
  public void deleteById(Integer id) {
    userRepository.deleteById(id);
  }

  @Override
  public Boolean existsByRoleDomain_Id(Integer id) {
    return userRepository.existsByRoleJpaEntity_Id(id);
  }
}
