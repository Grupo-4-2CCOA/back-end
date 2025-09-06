package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.application.usecases.schedule.*;
import sptech.school.projetoPI.application.usecases.scheduleItem.*;
import sptech.school.projetoPI.core.gateways.ScheduleItemGateway;

@Configuration
public class ScheduleItemConfig {

    @Bean
    public CreateScheduleItemUseCase createScheduleItemUseCase(ScheduleItemGateway scheduleItemGateway) {
        return new CreateScheduleItemUseCase(scheduleItemGateway);
    }

    @Bean
    public DeleteScheduleItemByIdUseCase deleteScheduleItemByIdUseCase(ScheduleItemGateway scheduleItemGateway) {
        return new DeleteScheduleItemByIdUseCase(scheduleItemGateway);
    }

    @Bean
    public GetAllScheduleItemUseCase getAllScheduleItemUseCase(ScheduleItemGateway scheduleItemGateway) {
        return new GetAllScheduleItemUseCase(scheduleItemGateway);
    }

    @Bean
    public GetScheduleItemByIdUseCase getScheduleItemByIdUseCase(ScheduleItemGateway scheduleItemGateway) {
        return new GetScheduleItemByIdUseCase(scheduleItemGateway);
    }

    @Bean
    public UpdateScheduleItemByIdUseCase updateScheduleItemByIdUseCase(ScheduleItemGateway scheduleItemGateway) {
        return new UpdateScheduleItemByIdUseCase(scheduleItemGateway);
    }

}
