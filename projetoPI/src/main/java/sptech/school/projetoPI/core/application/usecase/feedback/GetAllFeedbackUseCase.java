package sptech.school.projetoPI.core.application.usecase.feedback;

import sptech.school.projetoPI.core.domain.FeedbackDomain;
import sptech.school.projetoPI.core.gateway.FeedbackGateway;

import java.util.List;

public class GetAllFeedbackUseCase {


    private final FeedbackGateway feedbackGateway;

    public GetAllFeedbackUseCase(FeedbackGateway feedbackGateway) {
        this.feedbackGateway = feedbackGateway;
    }

    public List<FeedbackDomain> execute() {
        return feedbackGateway.findAll();
    }
}
