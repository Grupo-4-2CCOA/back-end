package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.core.application.usecase.availability.*;
import sptech.school.projetoPI.core.gateway.AvailabilityGateway;

@Configuration
public class AvailabilityConfig {

    @Bean
    public CreateAvailabilityUseCase createAvailabilityUseCase(AvailabilityGateway availabilityGateway) {
        return new CreateAvailabilityUseCase(availabilityGateway);
    }

    @Bean
    public GetAllAvailabilityUseCase getAllAvailabilityUseCase(AvailabilityGateway availabilityGateway) {
        return new GetAllAvailabilityUseCase(availabilityGateway);
    }

    @Bean
    public GetAvailabilityByIdUseCase getAvailabilityByIdUseCase(AvailabilityGateway availabilityGateway) {
        return new GetAvailabilityByIdUseCase(availabilityGateway);
    }

    @Bean
    public UpdateAvailabilityByIdUseCase updateAvailabilityByIdUseCase(AvailabilityGateway availabilityGateway) {
        return new UpdateAvailabilityByIdUseCase(availabilityGateway);
    }

    @Bean
    public DeleteAvailabilityByIdUseCase deleteAvailabilityByIdUseCase(AvailabilityGateway availabilityGateway) {
        return new DeleteAvailabilityByIdUseCase(availabilityGateway);
    }
}

