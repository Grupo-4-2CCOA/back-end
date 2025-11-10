package sptech.school.projetoPI.core.application.usecases.feedback;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.FeedbackDomain;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;

public class UpdateFeedbackByIdUseCase {

    private final FeedbackGateway feedbackGateway;
    private final ScheduleGateway scheduleGateway;

    public UpdateFeedbackByIdUseCase(FeedbackGateway feedbackGateway, ScheduleGateway scheduleGateway, UserGateway clientGateway) {
        this.feedbackGateway = feedbackGateway;
        this.scheduleGateway = scheduleGateway;
    }

    public FeedbackDomain execute(FeedbackDomain feedbackDomain, Integer id) {
        if (!feedbackGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O feedback com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (!scheduleGateway.existsById(feedbackDomain.getScheduleDomain().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(feedbackDomain.getScheduleDomain().getId())
            );
        }

        feedbackDomain.setId(id);
        feedbackDomain.setCreatedAt(feedbackGateway.findById(id).get().getCreatedAt());
        feedbackDomain.setUpdatedAt(LocalDateTime.now());
        return feedbackGateway.save(feedbackDomain);
    }
}
