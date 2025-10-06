package sptech.school.projetoPI.refactor.infraestructure.mapper;

import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.UserJpaEntity;

public class UserMapper {
  // UserJpaEntity -> UserDomain
  public static UserDomain toUserDomain(UserJpaEntity userJpaEntity) {
    if (userJpaEntity == null) {
      return null;
    }

    UserDomain userDomain = new UserDomain();

    userDomain.setId(userJpaEntity.getId());
    userDomain.setActive(userJpaEntity.getIsActive());
    userDomain.setCreatedAt(userJpaEntity.getCreatedAt());
    userDomain.setUpdatedAt(userJpaEntity.getUpdatedAt());
    userDomain.setCpf(userJpaEntity.getCpf());
    userDomain.setEmail(userJpaEntity.getEmail());
    userDomain.setPhone(userJpaEntity.getPhone());
    userDomain.setCep(userJpaEntity.getCep());
    userDomain.setRoleDomain(RoleMapper.toRoleDomain(userJpaEntity.getRoleJpaEntity()));

    return userDomain;
  }

  // UserDomain -> UserJpaEntity
  public static UserJpaEntity toUserJpaEntity(UserDomain userDomain) {
    if (userDomain == null) {
      return null;
    }

    UserJpaEntity userJpaEntity = new UserJpaEntity();

    userJpaEntity.setId(userDomain.getId());
    userJpaEntity.setIsActive(userDomain.getActive());
    userJpaEntity.setCreatedAt(userDomain.getCreatedAt());
    userJpaEntity.setUpdatedAt(userDomain.getUpdatedAt());
    userJpaEntity.setName(userDomain.getName());
    userJpaEntity.setCpf(userDomain.getCpf());
    userJpaEntity.setEmail(userDomain.getEmail());
    userJpaEntity.setPhone(userDomain.getPhone());
    userJpaEntity.setCep(userDomain.getCep());
    userJpaEntity.setRoleJpaEntity(RoleMapper.toRoleJpaEntity(userDomain.getRoleDomain()));

    return userJpaEntity;
  }
}
