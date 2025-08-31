package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.application.usecases.employee.*;
import sptech.school.projetoPI.application.usecases.availability.ValidateRequestBodyUseCase; // Assumindo que este use case é compartilhado
import sptech.school.projetoPI.core.gateways.EmployeeGateway;
import sptech.school.projetoPI.core.gateways.RoleGateway; // Se o EmployeeUseCase precisar de RoleGateway

@Configuration
public class EmployeeConfig {

    @Bean
    public CreateEmployeeUseCase createEmployeeUseCase(EmployeeGateway employeeGateway, ValidateRequestBodyUseCase validateRequestBodyUseCase) {
        return new CreateEmployeeUseCase(employeeGateway, validateRequestBodyUseCase);
    }

    @Bean
    public GetAllEmployeeUseCase getAllEmployeesUseCase(EmployeeGateway employeeGateway) {
        return new GetAllEmployeeUseCase(employeeGateway);
    }

    @Bean
    public GetEmployeeByIdUseCase getEmployeeByIdUseCase(EmployeeGateway employeeGateway) {
        return new GetEmployeeByIdUseCase(employeeGateway);
    }

    @Bean
    public UpdateEmployeeByIdUseCase updateEmployeeByIdUseCase(EmployeeGateway employeeGateway, ValidateRequestBodyUseCase validateRequestBodyUseCase) {
        return new UpdateEmployeeByIdUseCase(employeeGateway, validateRequestBodyUseCase);
    }

    @Bean
    public DeleteEmployeeByIdUseCase deleteEmployeeByIdUseCase(EmployeeGateway employeeGateway) {
        return new DeleteEmployeeByIdUseCase(employeeGateway);
    }
}