package sptech.school.projetoPI.application.usecases.feedback;

import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;

import java.util.List;

public class GetAllFeedbackUseCase {


    private final FeedbackGateway feedbackGateway;

    public GetAllFeedbackUseCase(FeedbackGateway feedbackGateway) {
        this.feedbackGateway = feedbackGateway;
    }

    public List<Feedback> execute() {
        return feedbackGateway.findAll();
    }
}
