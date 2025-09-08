package sptech.school.projetoPI.core.application.usecase.feedback;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domain.FeedbackDomain;
import sptech.school.projetoPI.core.gateway.ClientGateway;
import sptech.school.projetoPI.core.gateway.FeedbackGateway;
import sptech.school.projetoPI.core.gateway.ScheduleGateway;

import java.time.LocalDateTime;

public class CreateFeedbackUseCase {

    private final FeedbackGateway feedbackGateway;
    private final ScheduleGateway scheduleGateway;
    private final ClientGateway clientGateway;

    public CreateFeedbackUseCase(FeedbackGateway feedbackGateway, ScheduleGateway scheduleGateway, ClientGateway clientGateway) {
        this.feedbackGateway = feedbackGateway;
        this.scheduleGateway = scheduleGateway;
        this.clientGateway = clientGateway;
    }

    public FeedbackDomain execute(FeedbackDomain feedbackDomain) {
        if (!scheduleGateway.existsById(feedbackDomain.getSchedule().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(feedbackDomain.getSchedule().getId())
            );
        }

        if (!clientGateway.existsByIdAndActiveTrue(feedbackDomain.getClient().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O cliente com o ID %d não foi encontrado".formatted(feedbackDomain.getClient().getId())
            );
        }

        feedbackDomain.setId(null);
        feedbackDomain.setCreatedAt(LocalDateTime.now());
        feedbackDomain.setUpdatedAt(LocalDateTime.now());
        return feedbackGateway.save(feedbackDomain);
    }
}
