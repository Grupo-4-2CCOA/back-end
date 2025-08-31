package sptech.school.projetoPI.application.usecases.feedback;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.user.FeedbackValidationUseCase;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;

import java.time.LocalDateTime;

public class UpdateFeedbackByIdUseCase {

    private final FeedbackGateway feedbackGateway;
    private final FeedbackValidationUseCase feedbackValidationUseCase;

    public UpdateFeedbackByIdUseCase(FeedbackGateway feedbackGateway, FeedbackValidationUseCase feedbackValidationUseCase) {
        this.feedbackGateway = feedbackGateway;
        this.feedbackValidationUseCase = feedbackValidationUseCase;
    }

    public Feedback execute(Feedback feedback, Integer id) {
        if (!feedbackGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O feedback com o ID %d não foi encontrado".formatted(id)
            );
        }

        feedbackValidationUseCase.validateRequestBody(feedback);
        feedback.setId(id);
        feedback.setCreatedAt(feedbackGateway.findById(id).get().getCreatedAt());
        feedback.setUpdatedAt(LocalDateTime.now());
        return feedbackGateway.save(feedback);
    }
}
