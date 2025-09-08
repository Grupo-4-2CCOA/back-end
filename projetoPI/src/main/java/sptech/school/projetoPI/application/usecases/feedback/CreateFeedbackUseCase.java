package sptech.school.projetoPI.application.usecases.feedback;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

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

    public Feedback execute(Feedback feedback) {
        if (!scheduleGateway.existsById(feedback.getSchedule().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(feedback.getSchedule().getId())
            );
        }

        if (!clientGateway.existsByIdAndActiveTrue(feedback.getClient().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O cliente com o ID %d não foi encontrado".formatted(feedback.getClient().getId())
            );
        }

        feedback.setId(null);
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setUpdatedAt(LocalDateTime.now());
        return feedbackGateway.save(feedback);
    }
}
