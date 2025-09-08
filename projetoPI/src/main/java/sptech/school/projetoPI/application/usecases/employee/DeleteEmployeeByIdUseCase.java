package sptech.school.projetoPI.application.usecases.employee;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

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

        Employee employee = employeeGateway.findById(id).get();
        employee.setActive(false);
        employee.setUpdatedAt(LocalDateTime.now());
        employeeGateway.save(employee);
    }
}
