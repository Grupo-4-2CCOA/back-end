package sptech.school.projetoPI.application.usecases.employee;

import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;

import java.util.List;

public class GetAllEmployeeUseCase {

    private final EmployeeGateway employeeGateway;

    public GetAllEmployeeUseCase(EmployeeGateway employeeGateway) {
        this.employeeGateway = employeeGateway;
    }

    public List<Employee> execute() {
        return employeeGateway.findAllByActiveTrue();
    }
}
