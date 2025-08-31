package sptech.school.projetoPI.application.usecases.employee;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.user.EmployeeValidationUseCase;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;

import java.time.LocalDateTime;

public class CreateEmployeeUseCase {

    private final EmployeeGateway repository;
    private final ClientGateway clientGateway;
    private final EmployeeValidationUseCase employeeValidationUseCase;

    public CreateEmployeeUseCase(EmployeeGateway repository, ClientGateway clientGateway, EmployeeValidationUseCase employeeValidationUseCase) {
        this.repository = repository;
        this.clientGateway = clientGateway;
        this.employeeValidationUseCase = employeeValidationUseCase;
    }

    public Employee execute(Employee employee) {
        clientGateway.validateUniqueProperties(employee.getCpf(), employee.getEmail(), employee.getPhone());
        employeeValidationUseCase.validateRequestBody(employee);

        if (roleRepository.findById(employee.getRole().getId()).get().getName().equals("OWNER") && repository.existsByRoleName("OWNER")) {
            throw new EntityConflictException(
                    Logs.POST_ROLE_CONFLICT.getMessage()
            );
        }

        String senhaCriptografada = passwordEncoder.encode(employee.getPassword());

        employee.setId(null);
        employee.setPassword(senhaCriptografada);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        return repository.save(employee);
    }
}
