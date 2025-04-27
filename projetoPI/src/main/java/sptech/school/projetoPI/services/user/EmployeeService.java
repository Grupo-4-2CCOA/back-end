package sptech.school.projetoPI.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Availability;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.exceptions.exceptionClass.*;
import sptech.school.projetoPI.repositories.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final AvailabilityRepository availabilityRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public Employee signEmployee(Employee employee) {
        userService.validateUniqueProperties(employee.getCpf(), employee.getEmail(), employee.getPhone());

        if(!roleRepository.existsById(employee.getRole().getId())) {
            throw new EntityNotFoundException(
                    "O cargo de ID %d não foi encontrado".formatted(employee.getRole().getId())
            );
        }

        if (roleRepository.findById(employee.getRole().getId()).get().getName().equals("OWNER") && repository.existsByRoleName("OWNER")) {
            throw new EntityConflictException(
                    "Já existe um funcionário como 'dono'"
            );
        }

        validateRequestBody(employee);
        employee.setId(null);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAllByActiveTrue();
    }

    public Employee getEmployeeById(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O funcionário de ID %d não foi encontrado".formatted(id)
                )
        );
    }

    public Employee updateEmployeeById(Employee employee, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O funcionário com o ID %d não foi encontrado".formatted(id)
            );
        }

        if(repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O funcionário com o ID %d está inativo".formatted(id)
            );
        }

        if(!roleRepository.existsById(employee.getRole().getId())) {
            throw new EntityNotFoundException(
                    "O cargo de ID %d não foi encontrado".formatted(employee.getRole().getId())
            );
        }

        if (roleRepository.findById(employee.getRole().getId()).get().getName().equals("OWNER") && repository.existsByIdNotAndRoleName(id, "OWNER")) {
            throw new EntityConflictException(
                    "Já existe um funcionário como 'dono'"
            );
        }

        userService.validateUniquePropertiesOnUpdate(id, employee.getCpf(), employee.getEmail(), employee.getPhone(), false);
        validateRequestBody(employee);
        employee.setId(id);
        employee.setCreatedAt(repository.findById(id).get().getCreatedAt());
        employee.setUpdatedAt(LocalDateTime.now());
        return repository.save(employee);
    }

    public void deleteEmployeeById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O funcionário de ID %d não foi encontrado".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O funcionário com o ID %d já está inativo".formatted(id)
            );
        }

        if (scheduleRepository.existsByEmployeeId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes agendamentos estão relacionados com este funcionário: %s".formatted(scheduleRepository.findAllByEmployeeId(id)
                            .stream().map(Schedule::getId).toList())
            );
        }

        if (availabilityRepository.existsByEmployeeId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes dias de disponibilidade estão relacionados com este funcionário: %s".formatted(availabilityRepository.findAllByEmployeeId(id)
                            .stream().map(Availability::getId).toList())
            );
        }

        Employee employee = repository.findById(id).get();
        employee.setActive(false);
        employee.setUpdatedAt(LocalDateTime.now());
        repository.save(employee);
    }

    // Validação do POST & PUT
    private void validateRequestBody(Employee employee) {
        if (!roleRepository.existsById(employee.getRole().getId())) {
            throw new RelatedEntityNotFoundException(
                    "O cargo com o ID %d não foi encontrado".formatted(employee.getRole().getId())
            );
        }
    }
}