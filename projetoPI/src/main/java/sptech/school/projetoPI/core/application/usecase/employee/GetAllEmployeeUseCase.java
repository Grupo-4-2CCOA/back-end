package sptech.school.projetoPI.core.application.usecase.employee;

import sptech.school.projetoPI.core.domain.EmployeeDomain;
import sptech.school.projetoPI.core.gateway.EmployeeGateway;

import java.util.List;

public class GetAllEmployeeUseCase {

    private final EmployeeGateway employeeGateway;

    public GetAllEmployeeUseCase(EmployeeGateway employeeGateway) {
        this.employeeGateway = employeeGateway;
    }

    public List<EmployeeDomain> execute() {
        return employeeGateway.findAllByActiveTrue();
    }
}
