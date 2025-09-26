package sptech.school.projetoPI.refactor.infraestructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.refactor.core.application.usecase.role.CreateRoleUsecase;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.adapter.RoleAdapter;

@Configuration
public class RoleConfig {
  @Bean
  public CreateRoleUsecase createRoleUsecase(RoleAdapter roleAdapter) {
    return new CreateRoleUsecase(roleAdapter);
  }
}
