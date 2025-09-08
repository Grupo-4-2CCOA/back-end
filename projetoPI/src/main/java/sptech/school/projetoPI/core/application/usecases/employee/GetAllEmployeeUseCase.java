package sptech.school.projetoPI.core.application.usecases.employee;

import sptech.school.projetoPI.core.domains.EmployeeDomain;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;

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
