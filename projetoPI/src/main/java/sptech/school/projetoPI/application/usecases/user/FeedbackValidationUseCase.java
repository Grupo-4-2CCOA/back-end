package sptech.school.projetoPI.application.usecases.user;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.Feedback;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

public class FeedbackValidationUseCase {

    private final ScheduleGateway scheduleGateway;
    private final ClientGateway clientGateway;

    public FeedbackValidationUseCase(ScheduleGateway scheduleGateway, ClientGateway clientGateway) {
        this.scheduleGateway = scheduleGateway;
        this.clientGateway = clientGateway;
    }

    public void validateRequestBody(Feedback feedback) {
        if (!scheduleGateway.existsById(feedback.getSchedule().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O agendamento com o ID %d não foi encontrado".formatted(feedback.getSchedule().getId())
            );
        }

        if (!clientGateway.existsByIdAndActiveTrue(feedback.getClient().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O cliente com o ID %d não foi encontrado".formatted(feedback.getClient().getId())
            );
        }
    }
}
