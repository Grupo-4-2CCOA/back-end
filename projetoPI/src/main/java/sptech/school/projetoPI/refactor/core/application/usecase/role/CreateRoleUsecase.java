package sptech.school.projetoPI.refactor.core.application.usecase.role;

import sptech.school.projetoPI.refactor.core.application.command.role.CreateRoleCommand;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.CreateRoleInvalidNameException;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;

public class CreateRoleUsecase {
  private final RoleGateway roleGateway;

  public CreateRoleUsecase(RoleGateway roleGateway) {
    this.roleGateway = roleGateway;
  }

  public RoleDomain execute(CreateRoleCommand createRoleCommand) {
    if (createRoleCommand == null) {
      // exception interna ao backend, classe padrão do java:
      throw new IllegalArgumentException("`createRoleCommand` não pode ser nulo.");
    }

    // inicia a criação do domain:
    RoleDomain roleDomain = new RoleDomain();

    // popula o nome do domain:
    roleDomain.setName(createRoleCommand.name());

    if (roleGateway.existsByName(roleDomain.getName())) {
      throw new CreateRoleInvalidNameException("Já existe um cargo com este nome.");
    }

    // popula o nome do domain:
    roleDomain.setDescription(createRoleCommand.description());

    // salva a persistência:
    return roleGateway.save(roleDomain);
  }
}
