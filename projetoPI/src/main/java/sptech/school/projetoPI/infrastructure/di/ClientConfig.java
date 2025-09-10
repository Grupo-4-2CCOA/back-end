package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.core.application.usecases.client.*;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

@Configuration
public class ClientConfig {

    @Bean
    public CreateClientUseCase createClientUseCase(ClientGateway repository, PasswordEncoder passwordEncoder) {
        return new CreateClientUseCase(repository, passwordEncoder);
    }

    @Bean
    public GetAllClientsUseCase getAllClientsUseCase(ClientGateway clientGateway) {
        return new GetAllClientsUseCase(clientGateway);
    }

    @Bean
    public GetClientByIdUseCase getClientByIdUseCase(ClientGateway clientGateway) {
        return new GetClientByIdUseCase(clientGateway);
    }

    @Bean
    public UpdateClientByIdUseCase updateClientByIdUseCase(ClientGateway clientGateway) {
        return new UpdateClientByIdUseCase(clientGateway);
    }

    @Bean
    public DeleteClientByIdUseCase deleteClientByIdUseCase(ClientGateway clientGateway, ScheduleGateway scheduleGateway, FeedbackGateway feedbackGateway) {
        return new DeleteClientByIdUseCase(clientGateway, scheduleGateway, feedbackGateway);
    }
}