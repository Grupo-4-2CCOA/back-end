package sptech.school.projetoPI.old.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.core.application.usecases.employee.*;
import sptech.school.projetoPI.core.gateways.*;
import sptech.school.projetoPI.old.core.application.usecases.employee.*;
import sptech.school.projetoPI.old.core.gateways.*;

@Configuration
public class EmployeeConfig {

    @Bean
    public CreateEmployeeUseCase createEmployeeUseCase(ClientGateway clientGateway, EmployeeGateway employeeGateway, PasswordEncoder passwordEncoder, RoleGateway roleGateway) {
        return new CreateEmployeeUseCase(clientGateway, employeeGateway, passwordEncoder, roleGateway);
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
    public UpdateEmployeeByIdUseCase updateEmployeeByIdUseCase(ClientGateway clientGateway, EmployeeGateway employeeGateway, RoleGateway roleGateway) {
        return new UpdateEmployeeByIdUseCase(clientGateway, employeeGateway, roleGateway);
    }

    @Bean
    public DeleteEmployeeByIdUseCase deleteEmployeeByIdUseCase(EmployeeGateway repository, ScheduleGateway scheduleGateway, AvailabilityGateway availabilityGateway) {
        return new DeleteEmployeeByIdUseCase(repository,scheduleGateway,availabilityGateway);
    }
}
