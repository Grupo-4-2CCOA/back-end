package sptech.school.projetoPI.refactor.core.application.usecase.user;

import sptech.school.projetoPI.refactor.core.application.command.user.CreateUserCommand;
import sptech.school.projetoPI.refactor.core.application.exception.usecase.CreateUserInvalidRoleException;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;
import sptech.school.projetoPI.refactor.core.gateway.UserGateway;

import java.util.Optional;

public class CreateClientUsecase {
  private final UserGateway userGateway;
  private final RoleGateway roleGateway;

  public CreateClientUsecase(UserGateway userGateway, RoleGateway roleGateway) {
    this.userGateway = userGateway;
    this.roleGateway = roleGateway;
  }

  public UserDomain execute(CreateUserCommand createUserCommand) {
    if (createUserCommand == null) {
      // exception interna ao backend, classe padrão do java:
      throw new IllegalArgumentException("`createRoleCommand` não pode ser nulo.");
    }

    Optional<RoleDomain> roleDomain = roleGateway.findById(createUserCommand.roleId());
    if (roleDomain.isEmpty()) {
      throw new CreateUserInvalidRoleException("");
    }
  }
}
