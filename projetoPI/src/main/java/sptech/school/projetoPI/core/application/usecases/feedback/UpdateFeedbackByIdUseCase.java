package sptech.school.projetoPI.core.application.usecases.feedback;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.FeedbackDomain;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

import java.time.LocalDateTime;

public class UpdateFeedbackByIdUseCase {

    private final FeedbackGateway feedbackGateway;
    private final ScheduleGateway scheduleGateway;
    private final ClientGateway clientGateway;

    public UpdateFeedbackByIdUseCase(FeedbackGateway feedbackGateway, ScheduleGateway scheduleGateway, ClientGateway clientGateway) {
        this.feedbackGateway = feedbackGateway;
        this.scheduleGateway = scheduleGateway;
        this.clientGateway = clientGateway;
    }

    public FeedbackDomain execute(FeedbackDomain feedbackDomain, Integer id) {
        if (!feedbackGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O feedback com o ID %d não foi encontrado".formatted(id)
            );
        }

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

        feedbackDomain.setId(id);
        feedbackDomain.setCreatedAt(feedbackGateway.findById(id).get().getCreatedAt());
        feedbackDomain.setUpdatedAt(LocalDateTime.now());
        return feedbackGateway.save(feedbackDomain);
    }
}
