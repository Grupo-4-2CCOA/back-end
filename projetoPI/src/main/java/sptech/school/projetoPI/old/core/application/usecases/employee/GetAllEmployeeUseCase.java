package sptech.school.projetoPI.old.core.application.usecases.employee;

import sptech.school.projetoPI.old.core.domains.EmployeeDomain;
import sptech.school.projetoPI.old.core.gateways.EmployeeGateway;

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
