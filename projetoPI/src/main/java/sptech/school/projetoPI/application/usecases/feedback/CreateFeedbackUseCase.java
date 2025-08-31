package sptech.school.projetoPI.application.usecases.feedback;

import sptech.school.projetoPI.application.usecases.user.FeedbackValidationUseCase;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;

import java.time.LocalDateTime;

public class CreateFeedbackUseCase {

    private final FeedbackGateway feedbackGateway;
    private final FeedbackValidationUseCase feedbackValidationUseCase;

    public CreateFeedbackUseCase(FeedbackGateway feedbackGateway, FeedbackValidationUseCase feedbackValidationUseCase) {
        this.feedbackGateway = feedbackGateway;
        this.feedbackValidationUseCase = feedbackValidationUseCase;
    }

    public Feedback execute(Feedback feedback) {
        feedbackValidationUseCase.validateRequestBody(feedback);
        feedback.setId(null);
        feedback.setCreatedAt(LocalDateTime.now());
        feedback.setUpdatedAt(LocalDateTime.now());
        return feedbackGateway.save(feedback);
    }
}
