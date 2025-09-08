package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.core.application.usecases.role.*;
import sptech.school.projetoPI.core.gateways.RoleGateway;

@Configuration
public class RoleConfig {

    @Bean
    public CreateRoleUseCase createRoleUseCase(RoleGateway roleGateway) {
        return new CreateRoleUseCase(roleGateway);
    }

    @Bean
    public DeleteRoleByIdUseCase deleteRoleByIdUseCase(RoleGateway roleGateway) {
        return new DeleteRoleByIdUseCase(roleGateway);
    }

    @Bean
    public GetAllRoleUseCase getAllRoleUseCase(RoleGateway roleGateway) {
        return new GetAllRoleUseCase(roleGateway);
    }

    @Bean
    public GetRoleByIdUseCase getRoleByIdUseCase(RoleGateway roleGateway) {
        return new GetRoleByIdUseCase(roleGateway);
    }

    @Bean
    public UpdateRoleByIdUseCase updateRoleUseCase(RoleGateway roleGateway) {
        return new UpdateRoleByIdUseCase(roleGateway);
    }
}
