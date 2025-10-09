package sptech.school.projetoPI.refactor.core.application.usecase.role;

import sptech.school.projetoPI.refactor.core.application.command.role.UpdateRoleByIdCommand;
import sptech.school.projetoPI.refactor.core.application.exception.generic.NotFoundException;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.UpdateRoleInvalidNameException;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;

import java.util.Optional;

public class UpdateRoleByIdUsecase {
  private final RoleGateway roleGateway;

  public UpdateRoleByIdUsecase(RoleGateway roleGateway) {
    this.roleGateway = roleGateway;
  }

  public RoleDomain execute(UpdateRoleByIdCommand updateRoleByIdCommand) {
    if (updateRoleByIdCommand == null) {
      // exception interna ao backend, classe padrão do java:
      throw new IllegalArgumentException("`updateRoleByNameCommand` não pode ser nulo.");
    }
    if (updateRoleByIdCommand.id() == null) {
      throw new IllegalArgumentException("Para encontrar o cargo, o id não pode ser nulo.");
    }

    Optional<RoleDomain> optionalRoleDomain = roleGateway.findById(updateRoleByIdCommand.id());

    if (optionalRoleDomain.isEmpty()) {
      throw new NotFoundException("Cargo não encontrado.");
    }

    RoleDomain roleDomain = optionalRoleDomain.get();

    if (!roleDomain.getName().equals(updateRoleByIdCommand.name().trim()) && roleGateway.existsByName(updateRoleByIdCommand.name())) {
      throw new UpdateRoleInvalidNameException("Já existe um cargo com este nome.");
    }

    roleDomain.setName(updateRoleByIdCommand.name());

    String roleTrimmedDescription = updateRoleByIdCommand.description().trim();
    roleDomain.setDescription(roleTrimmedDescription);

    // atualiza a persistência:
    return roleGateway.save(optionalRoleDomain.get());
  }
}
