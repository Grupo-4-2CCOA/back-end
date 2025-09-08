package sptech.school.projetoPI.core.application.usecase.employee;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domain.EmployeeDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateway.AvailabilityGateway;
import sptech.school.projetoPI.core.gateway.EmployeeGateway;
import sptech.school.projetoPI.core.gateway.ScheduleGateway;

import java.time.LocalDateTime;

public class DeleteEmployeeByIdUseCase {

    private final EmployeeGateway employeeGateway;
    private final ScheduleGateway scheduleGateway;
    private final AvailabilityGateway availabilityGateway;

    public DeleteEmployeeByIdUseCase(EmployeeGateway employeeGateway, ScheduleGateway scheduleGateway, AvailabilityGateway availabilityGateway) {
        this.employeeGateway = employeeGateway;
        this.scheduleGateway = scheduleGateway;
        this.availabilityGateway = availabilityGateway;
    }

    public void execute(Integer id) {
        if (!employeeGateway.existsById(id)) {
            throw new EntityNotFoundException(
                    Logs.DELETE_NOT_FOUND.getMessage().formatted(id)
            );
        }

        if (employeeGateway.existsByIdAndActiveFalse(id)) {
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

        EmployeeDomain employeeDomain = employeeGateway.findById(id).get();
        employeeDomain.setActive(false);
        employeeDomain.setUpdatedAt(LocalDateTime.now());
        employeeGateway.save(employeeDomain);
    }
}
