package sptech.school.projetoPI.refactor.core.application.usecase.role;

import sptech.school.projetoPI.refactor.core.application.command.role.GetRoleByIdCommand;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.GetRoleInvalidIdException;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;

import java.util.Optional;

public class GetRoleByIdUsecase {
  private final RoleGateway roleGateway;

  public GetRoleByIdUsecase(RoleGateway roleGateway) {
    this.roleGateway = roleGateway;
  }

  public RoleDomain execute(GetRoleByIdCommand getRoleByIdCommand) {
    if (getRoleByIdCommand == null) {
      // exception interna ao backend, classe padrão do java:
      throw new IllegalArgumentException("`getRoleByIdCommand` não pode ser nulo.");
    }
    if (getRoleByIdCommand.id() == null) {
      throw new GetRoleInvalidIdException("Para encontrar o cargo, o id não pode ser nulo.");
    }

    Optional<RoleDomain> optionalRoleDomain = roleGateway.findById(getRoleByIdCommand.id());

    if (optionalRoleDomain.isEmpty()) {
      throw new GetRoleInvalidIdException("Cargo não encontrado.");
    }

    // retorna a persistência:
    return optionalRoleDomain.get();
  }
}
