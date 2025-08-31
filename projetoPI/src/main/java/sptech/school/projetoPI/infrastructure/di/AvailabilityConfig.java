package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.application.usecases.availability.*;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;

@Configuration
public class AvailabilityConfig {

    @Bean
    public CreateAvailabilityUseCase createAvailabilityUseCase(AvailabilityGateway availabilityGateway, ValidateRequestBodyUseCase validateRequestBodyUseCase) {
        return new CreateAvailabilityUseCase(availabilityGateway, validateRequestBodyUseCase);
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
    public UpdateAvailabilityByIdUseCase updateAvailabilityByIdUseCase(AvailabilityGateway availabilityGateway, ValidateRequestBodyUseCase validateRequestBodyUseCase) {
        return new UpdateAvailabilityByIdUseCase(availabilityGateway, validateRequestBodyUseCase);
    }

    @Bean
    public DeleteAvailabilityByIdUseCase deleteAvailabilityByIdUseCase(AvailabilityGateway availabilityGateway) {
        return new DeleteAvailabilityByIdUseCase(availabilityGateway);
    }
}

