package sptech.school.projetoPI.core.application.usecases.user.client;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.*;

import java.time.LocalDateTime;

public class DeleteClientByIdUseCase {

    private final UserGateway repository;
    private final ScheduleGateway scheduleRepository;


    public DeleteClientByIdUseCase(UserGateway repository, ScheduleGateway scheduleRepository, FeedbackGateway feedbackRepository) {
        this.repository = repository;
        this.scheduleRepository = scheduleRepository;

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

        UserDomain userDomain = repository.findById(id).get();
        userDomain.setActive(false);
        userDomain.setUpdatedAt(LocalDateTime.now());
        repository.save(userDomain);
    }
}
