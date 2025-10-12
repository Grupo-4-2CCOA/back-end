package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.core.application.usecases.user.employee.*;
import sptech.school.projetoPI.core.gateways.*;

@Configuration
public class EmployeeConfig {

    @Bean
    public CreateEmployeeUseCase createEmployeeUseCase(UserGateway userGateway, RoleGateway roleGateway) {
        return new CreateEmployeeUseCase(userGateway, roleGateway);
    }

    @Bean
    public GetAllEmployeeUseCase getAllEmployeesUseCase(UserGateway userGateway) {
        return new GetAllEmployeeUseCase(userGateway);
    }

    @Bean
    public GetEmployeeByIdUseCase getEmployeeByIdUseCase(UserGateway userGateway) {
        return new GetEmployeeByIdUseCase(userGateway);
    }

    @Bean
    public UpdateEmployeeByIdUseCase updateEmployeeByIdUseCase(UserGateway userGateway, RoleGateway roleGateway) {
        return new UpdateEmployeeByIdUseCase(userGateway, roleGateway);
    }

    @Bean
    public DeleteEmployeeByIdUseCase deleteEmployeeByIdUseCase(UserGateway userGateway, ScheduleGateway scheduleGateway, AvailabilityGateway availabilityGateway) {
        return new DeleteEmployeeByIdUseCase(userGateway,scheduleGateway,availabilityGateway);
    }
}
