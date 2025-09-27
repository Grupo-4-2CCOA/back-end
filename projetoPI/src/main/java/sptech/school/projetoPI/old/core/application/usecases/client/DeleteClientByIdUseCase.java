package sptech.school.projetoPI.old.core.application.usecases.client;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.old.core.domains.ClientDomain;
import sptech.school.projetoPI.old.core.enums.Logs;
import sptech.school.projetoPI.old.core.gateways.ClientGateway;
import sptech.school.projetoPI.old.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.old.core.gateways.ScheduleGateway;

import java.time.LocalDateTime;

public class DeleteClientByIdUseCase {

    private final ClientGateway repository;
    private final ScheduleGateway scheduleRepository;
    private final FeedbackGateway feedbackRepository;

    public DeleteClientByIdUseCase(ClientGateway repository, ScheduleGateway scheduleRepository, FeedbackGateway feedbackRepository) {
        this.repository = repository;
        this.scheduleRepository = scheduleRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public void execute(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    Logs.DELETE_NOT_FOUND.getMessage().formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    Logs.DELETE_INACTIVE_ENTITY.getMessage().formatted(id)
            );
        }

        if (scheduleRepository.existsByClientId(id)) {
            throw new ForeignKeyConstraintException(
                    Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Schedules", "Client")
            );
        }

        if (feedbackRepository.existsByClientId(id)) {
            throw new ForeignKeyConstraintException(
                    Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Feedbacks", "Client")
            );
        }

        ClientDomain clientDomain = repository.findById(id).get();
        clientDomain.setActive(false);
        clientDomain.setUpdatedAt(LocalDateTime.now());
        repository.save(clientDomain);
    }
}
