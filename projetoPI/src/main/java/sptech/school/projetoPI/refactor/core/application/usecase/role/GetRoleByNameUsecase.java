package sptech.school.projetoPI.refactor.core.application.usecase.role;

import sptech.school.projetoPI.refactor.core.application.command.role.GetRoleByNameCommand;
import sptech.school.projetoPI.refactor.core.application.exception.generic.NotFoundException;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.GetRoleInvalidNameException;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;

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
    if (getRoleByNameCommand.name() == null) {
      throw new GetRoleInvalidNameException("Para encontrar o cargo, o nome não pode ser nulo.");
    }
    if (getRoleByNameCommand.name().isBlank()) {
      throw new GetRoleInvalidNameException("Para encontrar o cargo, é preciso inserir um nome que não esteja em branco.");
    }

    // remove os espaços em branco no começo e no final da string:
    String roleTrimmedName = getRoleByNameCommand.name().trim();

    Optional<RoleDomain> optionalRoleDomain = roleGateway.findByName(roleTrimmedName);

    if (optionalRoleDomain.isEmpty()) {
      throw new NotFoundException("Cargo não encontrado.");
    }

    // retorna a persistência:
    return optionalRoleDomain.get();
  }
}
