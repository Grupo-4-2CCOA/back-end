package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.exceptions.EntityConflictException;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.ForeignKeyConstraintException;
import sptech.school.projetoPI.repositories.EmployeeRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final ScheduleRepository scheduleRepository;

    public EmployeeService(EmployeeRepository repository, ScheduleRepository scheduleRepository) {
        this.repository = repository;
        this.scheduleRepository = scheduleRepository;
    }

    public Employee signEmployee(Employee employee) {
        if (repository.existsByCpf(employee.getCpf()) || repository.existsByEmail(employee.getEmail()) || repository.existsByPhone(employee.getPhone())) {
            throw new EntityConflictException(
                    "Este CPF, email ou telefone já está cadastrado no banco."
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

    public ResponseEntity<Void> deleteEmployeeById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O funcionário de ID %d não foi encontrado".formatted(id)
            );
        }

        if(scheduleRepository.existsByEmployeeId(id)) {
            throw new ForeignKeyConstraintException(
                    "O funcionário de ID %d não pode ser apagado porque está relacionado com um ou vários agendamentos".formatted(id)
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
