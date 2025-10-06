package sptech.school.projetoPI.refactor.infraestructure.mapper;

import sptech.school.projetoPI.refactor.core.application.command.role.*;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.RoleJpaEntity;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role.*;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.response.role.RoleResponseDto;


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

  // UpdateRoleByIdRequestDto -> UpdateRoleByIdCommand
  public static UpdateRoleByIdCommand toUpdateRoleByIdCommand(UpdateRoleByIdRequestDto updateRoleByIdRequestDto, Integer id) {
    if (updateRoleByIdRequestDto == null) {
      return null;
    }

    return new UpdateRoleByIdCommand(
      id,
      updateRoleByIdRequestDto.name(),
      updateRoleByIdRequestDto.description()
    );
  }

  // RoleJpaEntity -> RoleDomain
  public static RoleDomain toRoleDomain(RoleJpaEntity roleJpaEntity) {
    if (roleJpaEntity == null) {
      return null;
    }

    RoleDomain roleDomain = new RoleDomain();

    roleDomain.setId(roleJpaEntity.getId());
    roleDomain.setActive(roleJpaEntity.getIsActive());
    roleDomain.setCreatedAt(roleJpaEntity.getCreatedAt());
    roleDomain.setUpdatedAt(roleJpaEntity.getUpdatedAt());
    roleDomain.setName(roleJpaEntity.getName());
    roleDomain.setDescription(roleJpaEntity.getDescription());

    return roleDomain;
  }

  // RoleDomain -> RoleJpaEntity
  public static RoleJpaEntity toRoleJpaEntity(RoleDomain roleDomain) {
    if (roleDomain == null) {
      return null;
    }

    RoleJpaEntity roleJpaEntity = new RoleJpaEntity();

    roleJpaEntity.setId(roleDomain.getId());
    roleJpaEntity.setIsActive(roleDomain.getActive());
    roleJpaEntity.setCreatedAt(roleDomain.getCreatedAt());
    roleJpaEntity.setUpdatedAt(roleDomain.getUpdatedAt());
    roleJpaEntity.setName(roleDomain.getName());
    roleJpaEntity.setDescription(roleDomain.getDescription());

    return roleJpaEntity;
  }

  // RoleDomain -> RoleResponseDto
  public static RoleResponseDto toRoleResponseDto(RoleDomain roleDomain) {
    if (roleDomain == null) {
      return null;
    }

    RoleResponseDto roleResponseDto = new RoleResponseDto(
      roleDomain.getId(),
      roleDomain.getName(),
      roleDomain.getDescription()
    );

    return roleResponseDto;
  }
}