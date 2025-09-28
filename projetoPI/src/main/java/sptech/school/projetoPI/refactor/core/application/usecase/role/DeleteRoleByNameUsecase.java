package sptech.school.projetoPI.refactor.core.application.usecase.role;

import sptech.school.projetoPI.refactor.core.application.command.role.DeleteRoleByNameCommand;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.DeleteRoleInvalidNameException;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.DeleteRoleUserHasRoleException;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;
import sptech.school.projetoPI.refactor.core.gateway.UserGateway;

public class DeleteRoleByNameUsecase {
  private final RoleGateway roleGateway;
  private final UserGateway userGateway;

  public DeleteRoleByNameUsecase(RoleGateway roleGateway, UserGateway userGateway) {
    this.roleGateway = roleGateway;
    this.userGateway = userGateway;
  }

  public void execute(DeleteRoleByNameCommand deleteRoleByNameCommand) {
    if (deleteRoleByNameCommand == null) {
      // exception interna ao backend, classe padrão do java:
      throw new IllegalArgumentException("`deleteRoleByNameCommand` não pode ser nulo.");
    }
    if (deleteRoleByNameCommand.name() == null) {
      throw new DeleteRoleInvalidNameException("Para deletar o cargo, o nome não pode ser nulo.");
    }
    if (deleteRoleByNameCommand.name().isBlank()) {
      throw new DeleteRoleInvalidNameException("Para deletar o cargo, é preciso inserir um nome que não esteja em branco.");
    }

    // TODO (desejável): criar métodos de tratamento para padronizar as formatações.
    // remove os espaços em branco no começo e no final da string:
    String roleTrimmedName = deleteRoleByNameCommand.name().trim();

    if (!roleGateway.existsByName(roleTrimmedName)) {
      throw new DeleteRoleInvalidNameException("Cargo não encontrado.");
    }

    if (userGateway.existsByRoleDomain_Name(roleTrimmedName)) {
      throw new DeleteRoleUserHasRoleException("Algum usuário ainda possui este cargo.");
    }

    // deleta a persistência:
    roleGateway.deleteByName(roleTrimmedName);
  }
}
