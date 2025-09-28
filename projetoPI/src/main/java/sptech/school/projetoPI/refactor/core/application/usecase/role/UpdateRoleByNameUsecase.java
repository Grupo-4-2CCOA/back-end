package sptech.school.projetoPI.refactor.core.application.usecase.role;

import sptech.school.projetoPI.refactor.core.application.command.role.UpdateRoleByNameCommand;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.UpdateRoleInvalidSearchNameException;
import sptech.school.projetoPI.refactor.core.application.exception.generic.MissingFieldsException;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;
import sptech.school.projetoPI.refactor.util.UtilValidator;

import java.util.Optional;

public class UpdateRoleByNameUsecase {
  private final RoleGateway roleGateway;

  public UpdateRoleByNameUsecase(RoleGateway roleGateway) {
    this.roleGateway = roleGateway;
  }

  public void execute(UpdateRoleByNameCommand updateRoleByNameCommand) {
    if (updateRoleByNameCommand == null) {
      // exception interna ao backend, classe padrão do java:
      throw new IllegalArgumentException("`updateRoleByNameCommand` não pode ser nulo.");
    }
    if (UtilValidator.stringIsNullOrBlank(updateRoleByNameCommand.searchName())) {
      throw new UpdateRoleInvalidSearchNameException("O nome atual do cargo (nome para a busca) não pode ser nulo.");
    }
    if (UtilValidator.stringIsNullOrBlank(updateRoleByNameCommand.newName())) {
      throw new MissingFieldsException("newName.");
    }
    if (UtilValidator.stringIsNullOrBlank(updateRoleByNameCommand.newDescription())) {
      throw new MissingFieldsException("newDescription.");
    }

    // TODO (desejável): criar métodos de tratamento para padronizar as formatações.
    // remove os espaços em branco no começo e no final da string:
    String roleTrimmedName = updateRoleByNameCommand.newName().trim();
    String roleTrimmedDescription = updateRoleByNameCommand.newDescription().get().trim();

    Optional<RoleDomain> optionalRoleDomain = roleGateway.findByName(roleTrimmedName);

    if (optionalRoleDomain.isEmpty()) {
      throw new UpdateRoleInvalidSearchNameException("Cargo não encontrado.");
    }

    RoleDomain roleDomain = optionalRoleDomain.get();

    roleDomain.setName(roleTrimmedName);
    roleDomain.setDescription(roleTrimmedDescription);

    // atualiza a persistência:
    roleGateway.save(optionalRoleDomain.get());
  }
}
