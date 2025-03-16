package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.enums.EmployeeRole;
import sptech.school.projetoPI.exceptions.EntityConflictException;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.EnumDoesntExistsException;
import sptech.school.projetoPI.exceptions.ForeignKeyConstraintException;
import sptech.school.projetoPI.repositories.EmployeeRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;
import sptech.school.projetoPI.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public EmployeeService(EmployeeRepository repository, ScheduleRepository scheduleRepository, UserRepository userRepository) {
        this.repository = repository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    public Employee signEmployee(Employee employee) {
        if(employee.getRole().equals(EmployeeRole.OWNER) && repository.existsByRole(employee.getRole())) {
            throw new EntityConflictException(
                    "Já existe um funcionário 'dono'"
            );
        }

        List<String> entidadesJaRegistradas = new ArrayList<>();

        if(repository.existsByCpf(employee.getCpf()) || userRepository.existsByCpf(employee.getCpf())) {
            entidadesJaRegistradas.add("CPF");
        }
        if(repository.existsByEmailIgnoreCase(employee.getEmail()) || userRepository.existsByEmailIgnoreCase(employee.getEmail())) {
            entidadesJaRegistradas.add("E-mail");
        }
        if(repository.existsByPhone(employee.getPhone()) || userRepository.existsByPhone(employee.getPhone())) {
            entidadesJaRegistradas.add("Telefone");
        }

        if(!entidadesJaRegistradas.isEmpty()) {
            throw new EntityConflictException(
                    "Os seguintes itens já foram registrados: " + entidadesJaRegistradas
            );
        }

        employee.setId(null);
        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O funcionário de id %d não foi encontrado".formatted(id)
                )
        );
    }

    public Employee updateEmployeeById(Employee employee, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O funcionário com o ID %d não foi encontrado".formatted(id)
            );
        }

        if(employee.getRole().equals(EmployeeRole.OWNER) && repository.existsByIdNotAndRole(id, employee.getRole())) {
            throw new EntityConflictException(
                    "Já existe um funcionário 'dono'"
            );
        }

        List<String> entidadesJaRegistradas = new ArrayList<>();

        if(repository.existsByIdNotAndCpf(id, employee.getCpf()) || userRepository.existsByCpf(employee.getCpf())) {
            entidadesJaRegistradas.add("CPF");
        }
        if(repository.existsByIdNotAndEmailIgnoreCase(id, employee.getEmail()) || userRepository.existsByEmailIgnoreCase(employee.getEmail())) {
            entidadesJaRegistradas.add("E-mail");
        }
        if(repository.existsByIdNotAndPhone(id, employee.getPhone()) || userRepository.existsByPhone(employee.getPhone())) {
            entidadesJaRegistradas.add("Telefone");
        }

        if(!entidadesJaRegistradas.isEmpty()) {
            throw new EntityConflictException(
                    "Os seguintes itens já foram registrados: " + entidadesJaRegistradas
            );
        }

        employee.setId(id);
        return repository.save(employee);
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
