package sptech.school.projetoPI.core.application.usecases.employee;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.EmployeeDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;

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
