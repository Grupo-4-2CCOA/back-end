package sptech.school.projetoPI.refactor.infraestructure.mapper;

import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.application.command.role.CreateRoleCommand;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.RoleJpaEntity;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.role.CreateRoleRequestDto;

public class RoleMapper {
  // CreateRoleRequestDto -> CreateRoleCommand
  public static CreateRoleCommand toCreateRoleCommand(CreateRoleRequestDto createRoleRequestDto) {
    if (createRoleRequestDto == null) {
      return null;
    }

    return new CreateRoleCommand(
      createRoleRequestDto.name(),
      createRoleRequestDto.description()
    );
  }

  // RoleDomain -> RoleJpaEntity
  public static RoleJpaEntity toRoleJpaEntity(RoleDomain roleDomain) {
    if (roleDomain == null) {
      return null;
    }

    return new RoleJpaEntity(
      roleDomain.getId(),
      roleDomain.getActive(),
      roleDomain.getCreatedAt(),
      roleDomain.getUpdatedAt(),
      roleDomain.getName(),
      roleDomain.getDescription(),
      roleDomain.getUserDomains()
    );
  }

  // RoleJpaEntity -> RoleDomain
  public static RoleDomain toRoleDomain(RoleJpaEntity roleJpaEntity) {
    if (roleJpaEntity == null) {
      return null;
    }

    RoleDomain roleDomain = new RoleDomain();

    roleDomain.setId(roleJpaEntity.getId());
    roleDomain.setActive(roleJpaEntity.getActive());
    roleDomain.setCreatedAt(roleJpaEntity.getCreatedAt());
    roleDomain.setUpdatedAt(roleJpaEntity.getUpdatedAt());
    roleDomain.setName(roleJpaEntity.getName());
    roleDomain.setDescription(roleJpaEntity.getDescription());
    roleDomain.setUserDomains(roleJpaEntity.getUserDomains());

    return roleDomain;
  }
}