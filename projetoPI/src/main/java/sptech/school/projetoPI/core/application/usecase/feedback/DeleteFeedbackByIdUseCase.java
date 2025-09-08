package sptech.school.projetoPI.core.application.usecase.feedback;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.gateway.FeedbackGateway;

public class DeleteFeedbackByIdUseCase {

    private final FeedbackGateway feedbackGateway;

    public DeleteFeedbackByIdUseCase(FeedbackGateway feedbackGateway) {
        this.feedbackGateway = feedbackGateway;
    }

    public void execute(Integer id) {
        if (!feedbackGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    "O feedback com o ID %d não foi encontrado".formatted(id)
            );
        }

        feedbackGateway.deleteById(id);
    }
}
