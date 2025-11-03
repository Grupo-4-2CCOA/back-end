package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.core.application.usecases.service.*;
import sptech.school.projetoPI.core.gateways.FileUploadGateway;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

@Configuration
public class ServiceConfig {
    @Bean
    public CreateServiceUseCase createServiceUseCase(ServiceGateway serviceGateway, FileUploadGateway fileUploadGateway) {
        return new CreateServiceUseCase(serviceGateway, fileUploadGateway);
    }

    @Bean
    public DeleteServiceByIdUseCase deleteServiceByIdUseCase(ServiceGateway serviceGateway) {
        return new DeleteServiceByIdUseCase(serviceGateway);
    }

    @Bean
    public GetAllServiceUseCase getAllServiceUseCase(ServiceGateway serviceGateway) {
        return new GetAllServiceUseCase(serviceGateway);
    }

    @Bean
    public GetServiceByIdUseCase getServiceByIdUseCase(ServiceGateway serviceGateway) {
        return new GetServiceByIdUseCase(serviceGateway);
    }

    @Bean
    public UpdateServiceByIdUseCase updateServiceByIdUseCase(ServiceGateway serviceGateway, FileUploadGateway fileUploadGateway) {
        return new UpdateServiceByIdUseCase(serviceGateway, fileUploadGateway);
    }
}
