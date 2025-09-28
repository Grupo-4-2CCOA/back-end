package sptech.school.projetoPI.old.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.core.application.usecases.scheduleItem.*;
import sptech.school.projetoPI.old.core.application.usecases.scheduleItem.*;
import sptech.school.projetoPI.old.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.old.core.gateways.ScheduleItemGateway;
import sptech.school.projetoPI.old.core.gateways.ServiceGateway;

@Configuration
public class ScheduleItemConfig {
    @Bean
    public CreateScheduleItemUseCase createScheduleItemUseCase(ScheduleItemGateway scheduleItemGateway, ScheduleGateway scheduleGateway, ServiceGateway serviceGateway) {
        return new CreateScheduleItemUseCase(scheduleItemGateway, scheduleGateway, serviceGateway);
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
    public UpdateScheduleItemByIdUseCase updateScheduleItemByIdUseCase(ScheduleItemGateway scheduleItemGateway, ScheduleGateway scheduleGateway, ServiceGateway serviceGateway) {
        return new UpdateScheduleItemByIdUseCase(scheduleItemGateway, scheduleGateway, serviceGateway);
    }
}
