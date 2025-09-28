package sptech.school.projetoPI.old.core.application.usecases.feedback;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.domains.FeedbackDomain;
import sptech.school.projetoPI.old.core.gateways.FeedbackGateway;

public class GetFeedbackByIdUseCase {

    private final FeedbackGateway feedbackGateway;

    public GetFeedbackByIdUseCase(FeedbackGateway feedbackGateway) {
        this.feedbackGateway = feedbackGateway;
    }

    public FeedbackDomain execute(Integer id) {
        return feedbackGateway.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O feedback com o ID %d não foi encontrado".formatted(id)
                )
        );
    }
}
