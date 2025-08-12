package sptech.school.projetoPI.services.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.enums.Logs;
import sptech.school.projetoPI.exceptions.exceptionClass.*;
import sptech.school.projetoPI.repositories.*;
import sptech.school.projetoPI.services.AbstractService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService extends AbstractService<Employee> {
    private final EmployeeRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final AvailabilityRepository availabilityRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Employee postMethod(Employee employee) {
        userService.validateUniqueProperties(employee.getCpf(), employee.getEmail(), employee.getPhone());
        validateRequestBody(employee);

        if (roleRepository.findById(employee.getRole().getId()).get().getName().equals("OWNER") && repository.existsByRoleName("OWNER")) {
            log.error(Logs.POST_ROLE_CONFLICT.getMessage());
            throw new EntityConflictException(
                    Logs.POST_ROLE_CONFLICT.getMessage()
            );
        }

        String senhaCriptografada = passwordEncoder.encode(employee.getPassword());

        employee.setId(null);
        employee.setPassword(senhaCriptografada);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        log.info(Logs.POST_SUCCESSFULLY.getMessage());
        return repository.save(employee);
    }

    @Override
    public List<Employee> getAllMethod() {
        if (repository.findAllByActiveTrue().isEmpty()) log.info(Logs.GET_ALL_SUCCESSFULLY_EMPTY.getMessage());
        else log.info(Logs.GET_ALL_SUCCESSFULLY.getMessage());

        return repository.findAllByActiveTrue();
    }

    @Override
    public Employee getByIdMethod(Integer id) {
        return repository.findByIdAndActiveTrue(id)
                .map((entity) -> {
                    log.info(Logs.GET_BY_ID_SUCCESSFULLY.getMessage().formatted(id));
                    return entity;
                })
                .orElseThrow(() -> {
                    log.error(Logs.GET_BY_ID_NOT_FOUND.getMessage().formatted(id));
                    return new EntityNotFoundException(
                            Logs.GET_BY_ID_NOT_FOUND.getMessage().formatted(id)
                    );
                });
    }

    @Override
    public Employee putByIdMethod(Employee employee, Integer id) {
        if (!repository.existsById(id)) {
            log.error(Logs.PUT_NOT_FOUND.getMessage().formatted(id));
            throw new EntityNotFoundException(
                    Logs.PUT_NOT_FOUND.getMessage().formatted(id)
            );
        }

        if(repository.existsByIdAndActiveFalse(id)) {
            log.error(Logs.PUT_INACTIVE_ENTITY.getMessage().formatted(id));
            throw new InactiveEntityException(
                    Logs.PUT_INACTIVE_ENTITY.getMessage().formatted(id)
            );
        }

        validateRequestBody(employee);

        if (roleRepository.findById(employee.getRole().getId()).get().getName().equals("OWNER") && repository.existsByIdNotAndRoleName(id, "OWNER")) {
            log.error(Logs.PUT_ROLE_CONFLICT.getMessage());
            throw new EntityConflictException(
                    Logs.PUT_ROLE_CONFLICT.getMessage()
            );
        }

        userService.validateUniquePropertiesOnUpdate(id, employee.getCpf(), employee.getEmail(), employee.getPhone(), false);
        employee.setId(id);
        employee.setCreatedAt(repository.findById(id).get().getCreatedAt());
        employee.setUpdatedAt(LocalDateTime.now());
        log.info(Logs.PUT_SUCCESSFULLY.getMessage().formatted(id));
        return repository.save(employee);
    }

    @Override
    public void deleteByIdMethod(Integer id) {
        if (!repository.existsById(id)) {
            log.error(Logs.DELETE_NOT_FOUND.getMessage().formatted(id));
            throw new EntityNotFoundException(
                    Logs.DELETE_NOT_FOUND.getMessage().formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            log.error(Logs.DELETE_INACTIVE_ENTITY.getMessage().formatted(id));
            throw new InactiveEntityException(
                    Logs.DELETE_INACTIVE_ENTITY.getMessage().formatted(id)
            );
        }

        if (scheduleRepository.existsByEmployeeId(id)) {
            log.error(Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Schedules", "Employee"));
            throw new ForeignKeyConstraintException(
                    Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Schedules", "Employee")
            );
        }

        if (availabilityRepository.existsByEmployeeId(id)) {
            log.error(Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Availabilities", "Employee"));
            throw new ForeignKeyConstraintException(
                    Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Availabilities", "Employee")
            );
        }

        Employee employee = repository.findById(id).get();
        employee.setActive(false);
        employee.setUpdatedAt(LocalDateTime.now());
        repository.save(employee);
        log.info(Logs.DELETE_SUCCESSFULLY.getMessage().formatted(id));
    }

    private void validateRequestBody(Employee employee) {
        if (!roleRepository.existsById(employee.getRole().getId())) {
            log.error(Logs.VALIDATE_REQUEST_BODY_ENTITY_NOT_FOUND.getMessage().formatted(employee.getRole().getId()));
            throw new RelatedEntityNotFoundException(
                    Logs.VALIDATE_REQUEST_BODY_ENTITY_NOT_FOUND.getMessage().formatted(employee.getRole().getId())
            );
        }
    }
}