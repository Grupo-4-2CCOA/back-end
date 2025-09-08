package sptech.school.projetoPI.core.application.usecase.employee;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domain.EmployeeDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateway.EmployeeGateway;

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
