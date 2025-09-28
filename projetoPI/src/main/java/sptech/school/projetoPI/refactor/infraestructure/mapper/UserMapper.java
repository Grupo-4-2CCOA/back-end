package sptech.school.projetoPI.refactor.infraestructure.mapper;

import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.UserJpaEntity;

public class UserMapper {
  // UserDomain -> UserJpaEntity
  public static UserJpaEntity toUserJpaEntity(UserDomain userDomain) {
    if (userDomain == null) {
      return null;
    }

    return new UserJpaEntity(
      userDomain.getId(),
      userDomain.getActive(),
      userDomain.getCreatedAt(),
      userDomain.getUpdatedAt(),
      userDomain.getName(),
      userDomain.getCpf(),
      userDomain.getEmail(),
      userDomain.getPhone(),
      userDomain.getCep(),
      RoleMapper.toRoleJpaEntity(userDomain.getRoleDomain())
    );
  }

  // UserJpaEntity -> UserDomain
  public static UserDomain toUserDomain(UserJpaEntity userJpaEntity) {
    if (userJpaEntity == null) {
      return null;
    }

    UserDomain userDomain = new UserDomain();

    userDomain.setId(userJpaEntity.getId());
    userDomain.setActive(userJpaEntity.getActive());
    userDomain.setCreatedAt(userJpaEntity.getCreatedAt());
    userDomain.setUpdatedAt(userJpaEntity.getUpdatedAt());
    userDomain.setCpf(userJpaEntity.getCpf());
    userDomain.setEmail(userJpaEntity.getEmail());
    userDomain.setPhone(userJpaEntity.getPhone());
    userDomain.setCep(userJpaEntity.getCep());
    userDomain.setRoleDomain(RoleMapper.toRoleDomain(userJpaEntity.getRoleJpaEntity()));

    return userDomain;
  }
}
