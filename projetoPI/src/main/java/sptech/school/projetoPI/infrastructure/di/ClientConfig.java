package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.core.application.usecases.user.client.*;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

@Configuration
public class ClientConfig {

    @Bean
    public CreateClientUseCase createClientUseCase(UserGateway userGateway, RoleGateway roleGateway) {
        return new CreateClientUseCase(userGateway, roleGateway);
    }

    @Bean
    public GetAllClientsUseCase getAllClientsUseCase(UserGateway userGateway) {
        return new GetAllClientsUseCase(userGateway);
    }

    @Bean
    public GetClientByIdUseCase getClientByIdUseCase(UserGateway userGateway) {
        return new GetClientByIdUseCase(userGateway);
    }

    @Bean
    public UpdateClientByIdUseCase updateClientByIdUseCase(UserGateway userGateway) {
        return new UpdateClientByIdUseCase(userGateway);
    }

    @Bean
    public DeleteClientByIdUseCase deleteClientByIdUseCase(UserGateway userGateway, ScheduleGateway scheduleGateway, FeedbackGateway feedbackGateway) {
        return new DeleteClientByIdUseCase(userGateway, scheduleGateway, feedbackGateway);
    }
}