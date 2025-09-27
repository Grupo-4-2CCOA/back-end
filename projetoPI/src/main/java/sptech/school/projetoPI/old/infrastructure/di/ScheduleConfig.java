package sptech.school.projetoPI.old.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.core.application.usecases.schedule.*;
import sptech.school.projetoPI.core.gateways.*;
import sptech.school.projetoPI.old.core.application.usecases.schedule.*;
import sptech.school.projetoPI.old.core.gateways.*;

@Configuration
public class ScheduleConfig {

    @Bean
    public CreateScheduleUseCase createScheduleUseCase(ScheduleGateway scheduleGateway, ClientGateway clientGateway, EmployeeGateway employeeGateway, PaymentTypeGateway paymentTypeGateway) {
        return new CreateScheduleUseCase(scheduleGateway, clientGateway, employeeGateway, paymentTypeGateway);
    }

    @Bean
    public DeleteScheduleByIdUseCase deleteScheduleByIdUseCase(ScheduleGateway scheduleGateway) {
        return new DeleteScheduleByIdUseCase(scheduleGateway);
    }

    @Bean
    public GetAllScheduleUseCase getAllScheduleUseCase(ScheduleGateway scheduleGateway) {
        return new GetAllScheduleUseCase(scheduleGateway);
    }

    @Bean
    public GetScheduleByIdUseCase getScheduleByIdUseCase(ScheduleGateway scheduleGateway) {
        return new GetScheduleByIdUseCase(scheduleGateway);
    }

    @Bean
    public UpdateScheduleByIdUseCase updateScheduleByIdUseCase(ScheduleGateway scheduleGateway, ClientGateway clientGateway, EmployeeGateway employeeGateway, PaymentTypeGateway paymentTypeGateway) {
        return new UpdateScheduleByIdUseCase(scheduleGateway, clientGateway, employeeGateway, paymentTypeGateway);
    }

    @Bean
    public GetServiceNamesByScheduleIdUseCase getServiceNamesByScheduleIdUseCase(ScheduleGateway scheduleGateway, ScheduleItemGateway scheduleItemGateway, ServiceGateway serviceGateway) {
        return new GetServiceNamesByScheduleIdUseCase(scheduleGateway, scheduleItemGateway ,serviceGateway);
    }
}
