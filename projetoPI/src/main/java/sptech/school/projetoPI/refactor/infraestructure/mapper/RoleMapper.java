package sptech.school.projetoPI.refactor.infraestructure.mapper;

import sptech.school.projetoPI.refactor.core.application.command.role.DeleteRoleByIdCommand;
import sptech.school.projetoPI.refactor.core.application.command.role.GetRoleByIdCommand;
import sptech.school.projetoPI.refactor.core.application.command.role.UpdateRoleByIdCommand;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.application.command.role.CreateRoleCommand;
import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.RoleJpaEntity;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.UserJpaEntity;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role.CreateRoleRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role.DeleteRoleByNameRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role.GetRoleByNameRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role.UpdateRoleByNameRequestDto;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
  // UserDomain -> UserJpaEntity;
  // igual ao UserMapper.toUserJpaEntity, masnesse caso, o valor do
  //   atributo roleJpaEntity é definido como nulo para impedir um loop infinito:
  private static UserJpaEntity toUserJpaEntity(UserDomain userDomain) {
    if (userDomain == null) {
      return null;
    }

    return new UserJpaEntity(
      userDomain.getName(),
      userDomain.getCpf(),
      userDomain.getEmail(),
      userDomain.getPhone(),
      userDomain.getCep()
    );
  }

  // GetRoleByNameRequestDto -> GetRoleByNameCommand
  public static GetRoleByIdCommand toGetRoleByNameCommand(GetRoleByNameRequestDto getRoleByNameRequestDto) {
    if (getRoleByNameRequestDto == null) {
      return null;
    }

    return new GetRoleByIdCommand(
      getRoleByNameRequestDto.name()
    );
  }

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

  // UpdateRoleByNameRequestDto -> UpdateRoleByNameCommand
  public static UpdateRoleByIdCommand toUpdateRoleByNameCommand(UpdateRoleByNameRequestDto updateRoleByNameRequestDto) {
    if (updateRoleByNameRequestDto == null) {
      return null;
    }

    return new UpdateRoleByIdCommand(
      updateRoleByNameRequestDto.searchName(),
      updateRoleByNameRequestDto.newName(),
      updateRoleByNameRequestDto.newDescription()
    );
  }

  // DeleteRoleByNameRequestDto -> DeleteRoleByNameCommand
  public static DeleteRoleByIdCommand toDeleteRoleByNameCommand(DeleteRoleByNameRequestDto deleteRoleByNameRequestDto) {
    if (deleteRoleByNameRequestDto == null) {
      return null;
    }

    return new DeleteRoleByIdCommand(
      deleteRoleByNameRequestDto.name()
    );
  }

  // RoleDomain -> RoleJpaEntity
  public static RoleJpaEntity toRoleJpaEntity(RoleDomain roleDomain) {
    if (roleDomain == null) {
      return null;
    }

    Set<UserDomain> userDomains = roleDomain.getUserDomains();
    Set<UserJpaEntity> userJpaEntities = null;

    if (userDomains != null) {
      userJpaEntities = userDomains.stream().map(RoleMapper::toUserJpaEntity).collect(Collectors.toSet());
    }

    return new RoleJpaEntity(
      roleDomain.getId(),
      roleDomain.getActive(),
      roleDomain.getCreatedAt(),
      roleDomain.getUpdatedAt(),
      roleDomain.getName(),
      roleDomain.getDescription(),
      userJpaEntities
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
    roleDomain.setUserDomains(roleJpaEntity.getUserJpaEntities().stream().map(UserMapper::toUserDomain).collect(Collectors.toSet()));

    return roleDomain;
  }
}