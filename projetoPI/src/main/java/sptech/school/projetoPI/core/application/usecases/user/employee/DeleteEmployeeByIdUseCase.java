package sptech.school.projetoPI.core.application.usecases.user.employee;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;

public class DeleteEmployeeByIdUseCase {

    private final UserGateway userGateway;
    private final ScheduleGateway scheduleGateway;
    private final AvailabilityGateway availabilityGateway;

    public DeleteEmployeeByIdUseCase(UserGateway userGateway, ScheduleGateway scheduleGateway, AvailabilityGateway availabilityGateway) {
        this.userGateway = userGateway;
        this.scheduleGateway = scheduleGateway;
        this.availabilityGateway = availabilityGateway;
    }

    public void execute(Integer id) {
        if (!userGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    Logs.DELETE_NOT_FOUND.getMessage().formatted(id)
            );
        }

        if (userGateway.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    Logs.DELETE_INACTIVE_ENTITY.getMessage().formatted(id)
            );
        }

        if (scheduleGateway.existsByEmployeeId(id)) {
            throw new ForeignKeyConstraintException(
                    Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Schedules", "Employee")
            );
        }

        if (availabilityGateway.existsByEmployeeId(id)) {
            throw new ForeignKeyConstraintException(
                    Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Availabilities", "Employee")
            );
        }

        UserDomain userDomain = userGateway.findById(id).get();
        userDomain.setActive(false);
        userDomain.setUpdatedAt(LocalDateTime.now());
        userGateway.save(userDomain);
    }
}
