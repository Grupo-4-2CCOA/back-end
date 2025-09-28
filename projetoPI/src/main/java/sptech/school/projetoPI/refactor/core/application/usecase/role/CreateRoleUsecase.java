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

  public void execute(CreateRoleCommand createRoleCommand) {
    if (createRoleCommand == null) {
      // exception interna ao backend, classe padrão do java:
      throw new IllegalArgumentException("`createRoleCommand` não pode ser nulo.");
    }
    if (createRoleCommand.name() == null) {
      throw new CreateRoleInvalidNameException("Para criar o cargo, o nome não pode ser nulo.");
    }
    if (createRoleCommand.name().isBlank()) {
      throw new CreateRoleInvalidNameException("Para criar o cargo, é preciso inserir um nome que não esteja em branco.");
    }

    // remove os espaços em branco no começo e no final da string:
    String roleTrimmedName = createRoleCommand.name().trim();

    if (roleGateway.existsByName(roleTrimmedName)) {
      throw new CreateRoleInvalidNameException("O cargo já foi cadastrado.");
    }

    // inicia a criação do domain:
    RoleDomain roleDomain = new RoleDomain();

    // popula os dados do domain:
    roleDomain.setName(createRoleCommand.name());
    roleDomain.setDescription(createRoleCommand.description());

    // salva a persistência:
    roleGateway.save(roleDomain);
  }
}
