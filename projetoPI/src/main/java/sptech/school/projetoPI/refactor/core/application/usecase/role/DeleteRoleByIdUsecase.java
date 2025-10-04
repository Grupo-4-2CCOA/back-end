package sptech.school.projetoPI.refactor.core.application.usecase.role;

import sptech.school.projetoPI.refactor.core.application.command.role.DeleteRoleByIdCommand;
import sptech.school.projetoPI.refactor.core.application.exception.generic.NotFoundException;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.DeleteRoleInvalidIdException;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.DeleteRoleUserHasRoleException;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;
import sptech.school.projetoPI.refactor.core.gateway.UserGateway;

public class DeleteRoleByIdUsecase {
  private final RoleGateway roleGateway;
  private final UserGateway userGateway;

  public DeleteRoleByIdUsecase(RoleGateway roleGateway, UserGateway userGateway) {
    this.roleGateway = roleGateway;
    this.userGateway = userGateway;
  }

  public void execute(DeleteRoleByIdCommand deleteRoleByIdCommand) {
    if (deleteRoleByIdCommand == null) {
      // exception interna ao backend, classe padrão do java:
      throw new IllegalArgumentException("`deleteRoleByIdCommand` não pode ser nulo.");
    }
    if (deleteRoleByIdCommand.id() == null) {
      throw new DeleteRoleInvalidIdException("Para deletar o cargo, o id não pode ser nulo.");
    }

    if (!roleGateway.existsById(deleteRoleByIdCommand.id())) {
      throw new NotFoundException("Cargo não encontrado.");
    }

    if (userGateway.existsByRoleDomain_Id(deleteRoleByIdCommand.id())) {
      throw new DeleteRoleUserHasRoleException("Algum usuário ainda possui este cargo.");
    }

    // deleta a persistência:
    roleGateway.deleteById(deleteRoleByIdCommand.id());
  }
}
