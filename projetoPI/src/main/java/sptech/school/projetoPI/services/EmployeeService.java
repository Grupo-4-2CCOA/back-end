package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.exceptions.EntityConflictException;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.repositories.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee signEmployee(Employee employee) {
        if (repository.existsByCpf(employee.getCpf()) || repository.existsByEmail(employee.getEmail()) || repository.existsByPhone(employee.getPhone())) {
            throw new EntityConflictException(
                    "Este cpf, email ou telefone já está cadastrado no banco."
            );
        }

        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O funcionário de id %d não foi encontrado.".formatted(id)
                )
        );
    }

    public Employee updateEmployeeById(Employee employee, Integer id) {
        return null;
        // Não sei como fazer esse.
    }

    // Não sei se isso tá certo..
    public ResponseEntity<Void> deleteEmployeeById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityConflictException(
                    "O funcionário de id %d não foi encontrado.".formatted(id)
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
