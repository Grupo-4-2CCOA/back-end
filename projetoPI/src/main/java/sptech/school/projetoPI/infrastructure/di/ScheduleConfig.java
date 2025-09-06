package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.application.usecases.schedule.*;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

@Configuration
public class ScheduleConfig {

    @Bean
    public CreateScheduleUseCase createScheduleUseCase(ScheduleGateway scheduleGateway) {
        return new CreateScheduleUseCase(scheduleGateway);
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
    public UpdateScheduleByIdUseCase updateScheduleByIdUseCase(ScheduleGateway scheduleGateway) {
        return new UpdateScheduleByIdUseCase(scheduleGateway);
    }

}
