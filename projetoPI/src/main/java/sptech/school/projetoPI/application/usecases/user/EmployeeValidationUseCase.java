package sptech.school.projetoPI.application.usecases.user;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.RoleGateway;

public class EmployeeValidationUseCase {

    private final RoleGateway roleGateway;

    public EmployeeValidationUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public void validateRequestBody(Employee employee) {
        if (!roleGateway.existsById(employee.getRole().getId())) {
            throw new RelatedEntityNotFoundException(
                    Logs.VALIDATE_REQUEST_BODY_ENTITY_NOT_FOUND.getMessage().formatted(employee.getRole().getId())
            );
        }
    }
}
