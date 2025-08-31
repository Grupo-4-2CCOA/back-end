package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.application.usecases.feedback.*;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.ClientGateway; // Para validação de relacionamento
import sptech.school.projetoPI.core.gateways.ScheduleGateway; // Para validação de relacionamento

@Configuration
public class FeedbackConfig {

    @Bean
    public CreateFeedbackUseCase createFeedbackUseCase(FeedbackGateway feedbackGateway, ClientGateway clientGateway, ScheduleGateway scheduleGateway) {
        return new CreateFeedbackUseCase(feedbackGateway, clientGateway, scheduleGateway);
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
    public UpdateFeedbackByIdUseCase updateFeedbackByIdUseCase(FeedbackGateway feedbackGateway) {
        return new UpdateFeedbackByIdUseCase(feedbackGateway);
    }

    @Bean
    public DeleteFeedbackByIdUseCase deleteFeedbackByIdUseCase(FeedbackGateway feedbackGateway) {
        return new DeleteFeedbackByIdUseCase(feedbackGateway);
    }
}