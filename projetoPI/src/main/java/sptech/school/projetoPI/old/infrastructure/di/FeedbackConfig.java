package sptech.school.projetoPI.old.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.core.application.usecases.feedback.*;
import sptech.school.projetoPI.old.core.application.usecases.feedback.*;
import sptech.school.projetoPI.old.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.old.core.gateways.ClientGateway;
import sptech.school.projetoPI.old.core.gateways.ScheduleGateway;

@Configuration
public class FeedbackConfig {

    @Bean
    public CreateFeedbackUseCase createFeedbackUseCase(FeedbackGateway feedbackGateway, ScheduleGateway scheduleGateway, ClientGateway clientGateway) {
        return new CreateFeedbackUseCase(feedbackGateway, scheduleGateway, clientGateway);
    }

    @Bean
    public GetAllFeedbackUseCase getAllFeedbacksUseCase(FeedbackGateway feedbackGateway) {
        return new GetAllFeedbackUseCase(feedbackGateway);
    }

    @Bean
    public GetFeedbackByIdUseCase getFeedbackByIdUseCase(FeedbackGateway feedbackGateway) {
        return new GetFeedbackByIdUseCase(feedbackGateway);
    }

    @Bean
    public UpdateFeedbackByIdUseCase updateFeedbackByIdUseCase(FeedbackGateway feedbackGateway, ScheduleGateway scheduleGateway, ClientGateway clientGateway) {
        return new UpdateFeedbackByIdUseCase(feedbackGateway, scheduleGateway, clientGateway);
    }

    @Bean
    public DeleteFeedbackByIdUseCase deleteFeedbackByIdUseCase(FeedbackGateway feedbackGateway) {
        return new DeleteFeedbackByIdUseCase(feedbackGateway);
    }
}
