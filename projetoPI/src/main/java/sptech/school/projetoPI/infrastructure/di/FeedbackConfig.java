package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.core.application.usecase.feedback.*;
import sptech.school.projetoPI.core.gateway.FeedbackGateway;
import sptech.school.projetoPI.core.gateway.ClientGateway;
import sptech.school.projetoPI.core.gateway.ScheduleGateway;

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
