package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.application.usecases.employee.*;
import sptech.school.projetoPI.application.usecases.availability.ValidateRequestBodyUseCase; // Assumindo que este use case é compartilhado
import sptech.school.projetoPI.application.usecases.user.EmployeeValidationUseCase;
import sptech.school.projetoPI.application.usecases.user.UserValidationUseCase;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;
import sptech.school.projetoPI.core.gateways.RoleGateway; // Se o EmployeeUseCase precisar de RoleGateway
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

@Configuration
public class EmployeeConfig {

    @Bean
    public CreateEmployeeUseCase createEmployeeUseCase(EmployeeGateway repository, PasswordEncoder passwordEncoder, UserValidationUseCase userValidationUseCase, RoleGateway roleGateway , EmployeeValidationUseCase employeeValidationUseCase) {
        return new CreateEmployeeUseCase(repository, passwordEncoder,userValidationUseCase,roleGateway,employeeValidationUseCase);
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
    public UpdateEmployeeByIdUseCase updateEmployeeByIdUseCase(EmployeeGateway employeeGateway, RoleGateway roleGateway, UserValidationUseCase userValidationUseCase, EmployeeValidationUseCase employeeValidationUseCase) {
        return new UpdateEmployeeByIdUseCase(employeeGateway, roleGateway, userValidationUseCase, employeeValidationUseCase);
    }

    @Bean
    public DeleteEmployeeByIdUseCase deleteEmployeeByIdUseCase(EmployeeGateway repository, ScheduleGateway scheduleGateway, AvailabilityGateway availabilityGateway) {
        return new DeleteEmployeeByIdUseCase(repository,scheduleGateway,availabilityGateway);
    }
}