package sptech.school.projetoPI.old.core.application.usecases.employee;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.domains.EmployeeDomain;
import sptech.school.projetoPI.old.core.enums.Logs;
import sptech.school.projetoPI.old.core.gateways.EmployeeGateway;

public class GetEmployeeByIdUseCase {

    private final EmployeeGateway employeeGateway;

    public GetEmployeeByIdUseCase(EmployeeGateway employeeGateway) {
        this.employeeGateway = employeeGateway;
    }

    public EmployeeDomain execute(Integer id) {
        return employeeGateway.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                     new EntityNotFoundException(
                            Logs.GET_BY_ID_NOT_FOUND.getMessage().formatted(id)
                    ));
    }
}
