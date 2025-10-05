package sptech.school.projetoPI.refactor.infraestructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.refactor.core.application.usecase.role.*;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.adapter.RoleAdapter;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.adapter.UserAdapter;

@Configuration
public class RoleConfig {

  @Bean
  public GetRoleByIdUsecase getRoleByIdUsecase(RoleAdapter roleAdapter) {
    return new GetRoleByIdUsecase(roleAdapter);
  }
  @Bean
  public GetRoleByNameUsecase getRoleByNameUsecase(RoleAdapter roleAdapter) {
    return new GetRoleByNameUsecase(roleAdapter);
  }
  @Bean
  public CreateRoleUsecase createRoleUsecase(RoleAdapter roleAdapter) {
    return new CreateRoleUsecase(roleAdapter);
  }
  @Bean
  public UpdateRoleByIdUsecase updateRoleByIdUsecase(RoleAdapter roleAdapter) {
    return new UpdateRoleByIdUsecase(roleAdapter);
  }
  @Bean
  public DeleteRoleByIdUsecase deleteRoleByIdUsecase(RoleAdapter roleAdapter, UserAdapter userAdapter) {
    return new DeleteRoleByIdUsecase(roleAdapter, userAdapter);
  }
}
