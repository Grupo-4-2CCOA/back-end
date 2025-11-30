package sptech.school.projetoPI.core.application.usecases.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.projetoPI.core.domains.FeedbackDomain;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;

import java.util.List;

public class GetAllFeedbackUseCase {


    private final FeedbackGateway feedbackGateway;

    public GetAllFeedbackUseCase(FeedbackGateway feedbackGateway) {
        this.feedbackGateway = feedbackGateway;
    }

    public Page<FeedbackDomain> execute(Pageable pageable) {
        return feedbackGateway.findAll(pageable);
    }
}
