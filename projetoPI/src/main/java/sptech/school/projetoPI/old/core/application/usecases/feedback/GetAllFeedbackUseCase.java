package sptech.school.projetoPI.old.core.application.usecases.feedback;

import sptech.school.projetoPI.old.core.domains.FeedbackDomain;
import sptech.school.projetoPI.old.core.gateways.FeedbackGateway;

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
