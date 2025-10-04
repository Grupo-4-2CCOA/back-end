package sptech.school.projetoPI.refactor.core.application.usecase.role;

import sptech.school.projetoPI.refactor.core.application.command.role.GetRoleByNameCommand;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.GetRoleInvalidNameException;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;
import sptech.school.projetoPI.refactor.util.UtilValidator;

import java.util.Optional;

public class GetRoleByNameUsecase {
  private final RoleGateway roleGateway;

  public GetRoleByNameUsecase(RoleGateway roleGateway) {
    this.roleGateway = roleGateway;
  }

  public RoleDomain execute(GetRoleByNameCommand getRoleByNameCommand) {
    if (getRoleByNameCommand == null) {
      // exception interna ao backend, classe padrão do java:
      throw new IllegalArgumentException("`getRoleByNameCommand` não pode ser nulo.");
    }
    if (UtilValidator.stringIsNullOrBlank(getRoleByNameCommand.name())) {
      throw new GetRoleInvalidNameException("Para encontrar o cargo, o nome não pode ser nulo ou estar vazio.");
    }

    Optional<RoleDomain> optionalRoleDomain = roleGateway.findByName(getRoleByNameCommand.name());

    if (optionalRoleDomain.isEmpty()) {
      throw new GetRoleInvalidNameException("Cargo não encontrado.");
    }

    // retorna a persistência:
    return optionalRoleDomain.get();
  }
}
