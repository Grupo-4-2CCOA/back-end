package sptech.school.projetoPI.refactor.core.application.usecase.role;

import sptech.school.projetoPI.refactor.core.application.command.role.UpdateRoleByIdCommand;
import sptech.school.projetoPI.refactor.core.application.exception.generic.MissingFieldsException;
import sptech.school.projetoPI.refactor.core.application.exception.generic.NotFoundException;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.UpdateRoleInvalidNameException;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;
import sptech.school.projetoPI.refactor.util.UtilValidator;

import java.util.Optional;

public class UpdateRoleByIdUsecase {
  private final RoleGateway roleGateway;

  public UpdateRoleByIdUsecase(RoleGateway roleGateway) {
    this.roleGateway = roleGateway;
  }

  public void execute(UpdateRoleByIdCommand updateRoleByIdCommand) {
    if (updateRoleByIdCommand == null) {
      // exception interna ao backend, classe padrão do java:
      throw new IllegalArgumentException("`updateRoleByNameCommand` não pode ser nulo.");
    }
    if (updateRoleByIdCommand.id() == null) {
      throw new IllegalArgumentException("Para encontrar o cargo, o id não pode ser nulo.");
    }
    if (updateRoleByIdCommand.name() == null) {
      throw new MissingFieldsException("name.");
    }
    if (UtilValidator.stringIsNullOrBlank(updateRoleByIdCommand.description())) {
      throw new MissingFieldsException("description (nulo ou em branco).");
    }

    // TODO (desejável): criar métodos de tratamento para padronizar as formatações.
    // remove os espaços em branco no começo e no final da string:
    String roleTrimmedName = updateRoleByIdCommand.name().trim();
    String roleTrimmedDescription = updateRoleByIdCommand.description().trim();

    Optional<RoleDomain> optionalRoleDomain = roleGateway.findById(updateRoleByIdCommand.id());

    if (optionalRoleDomain.isEmpty()) {
      throw new NotFoundException("Cargo não encontrado.");
    }
    if (roleGateway.existsByName(roleTrimmedName)) {
      throw new UpdateRoleInvalidNameException("Já existe um cargo com este nome.");
    }

    RoleDomain roleDomain = optionalRoleDomain.get();

    roleDomain.setName(roleTrimmedName);
    roleDomain.setDescription(roleTrimmedDescription);

    // atualiza a persistência:
    roleGateway.save(optionalRoleDomain.get());
  }
}
