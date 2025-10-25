package sptech.school.projetoPI.core.application.usecases.feedback;

import sptech.school.projetoPI.core.application.dto.feedback.FeedbackRequestDto;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.FeedbackDomain;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;
import java.util.Optional;

public class CreateFeedbackUseCase {

    private final FeedbackGateway feedbackGateway;
    private final ScheduleGateway scheduleGateway;
    private final UserGateway userGateway;

    public CreateFeedbackUseCase(FeedbackGateway feedbackGateway, ScheduleGateway scheduleGateway, UserGateway userGateway) {
        this.feedbackGateway = feedbackGateway;
        this.scheduleGateway = scheduleGateway;
        this.userGateway = userGateway;
    }

    public FeedbackDomain execute(FeedbackRequestDto feedbackRequestDto) {
        Optional<ScheduleDomain> scheduleDomain = scheduleGateway.findById(feedbackRequestDto.getScheduleId());

        if (scheduleDomain.isEmpty()) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(feedbackRequestDto.getScheduleId())
            );
        }

        FeedbackDomain feedbackDomain = new FeedbackDomain();

        feedbackDomain.setId(null);
        feedbackDomain.setCreatedAt(LocalDateTime.now());
        feedbackDomain.setUpdatedAt(LocalDateTime.now());
        feedbackDomain.setComment(feedbackRequestDto.getComment());
        feedbackDomain.setRating(feedbackRequestDto.getRating());
        feedbackDomain.setScheduleDomain(scheduleDomain.get());

        return feedbackGateway.save(feedbackDomain);
    }
}
