package sptech.school.projetoPI.refactor.infraestructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.refactor.core.application.usecase.role.CreateRoleUsecase;
import sptech.school.projetoPI.refactor.core.application.usecase.role.DeleteRoleByNameUsecase;
import sptech.school.projetoPI.refactor.core.application.usecase.role.GetRoleByNameUsecase;
import sptech.school.projetoPI.refactor.core.application.usecase.role.UpdateRoleByNameUsecase;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.adapter.RoleAdapter;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.adapter.UserAdapter;

@Configuration
public class RoleConfig {
  @Bean
  public GetRoleByNameUsecase getRoleByNameUsecase(RoleAdapter roleAdapter) {
    return new GetRoleByNameUsecase(roleAdapter);
  }
  @Bean
  public CreateRoleUsecase createRoleUsecase(RoleAdapter roleAdapter) {
    return new CreateRoleUsecase(roleAdapter);
  }
  @Bean
  public UpdateRoleByNameUsecase updateRoleByNameUsecase(RoleAdapter roleAdapter) {
    return new UpdateRoleByNameUsecase(roleAdapter);
  }
  @Bean
  public DeleteRoleByNameUsecase deleteRoleByNameUsecase(RoleAdapter roleAdapter, UserAdapter userAdapter) {
    return new DeleteRoleByNameUsecase(roleAdapter, userAdapter);
  }
}
