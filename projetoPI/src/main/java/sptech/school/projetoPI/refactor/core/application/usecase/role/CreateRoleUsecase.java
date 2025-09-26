package sptech.school.projetoPI.refactor.core.application.usecase.role;

import sptech.school.projetoPI.refactor.core.application.command.role.CreateRoleCommand;
import sptech.school.projetoPI.refactor.core.application.exception.InvalidRoleException;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;

public class CreateRoleUsecase {
  private final RoleGateway roleGateway;

  public CreateRoleUsecase(RoleGateway roleGateway) {
    this.roleGateway = roleGateway;
  }

  public void execute(CreateRoleCommand createRoleCommand) {
    if (createRoleCommand == null) {
      throw new InvalidRoleException("O cargo não pode ser nulo.");
    }
    if (createRoleCommand.name().isBlank()) {
      throw new InvalidRoleException("O cargo precisa de um nome que não esteja em branco.");
    }

    // remove os espaços em branco no começo e no final da string:
    String roleTrimmedName = createRoleCommand.name().trim();

    if (roleGateway.existsByName(roleTrimmedName)) {
      throw new InvalidRoleException("O cargo já foi cadastrado.");
    }

    // inicia a criação do domain:
    RoleDomain roleDomain = new RoleDomain();

    // popula os dados do domain:
    roleDomain.setName(createRoleCommand.name());
    roleDomain.setDescription(createRoleCommand.description());

    // salva o domain (que será convertido para o objeto adequado pelo gateway):
    roleGateway.save(roleDomain);
  }
}
