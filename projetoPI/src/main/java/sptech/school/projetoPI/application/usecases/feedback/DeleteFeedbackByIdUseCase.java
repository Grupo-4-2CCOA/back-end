package sptech.school.projetoPI.application.usecases.feedback;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;

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
