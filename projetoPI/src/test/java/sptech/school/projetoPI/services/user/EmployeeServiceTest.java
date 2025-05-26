package sptech.school.projetoPI.services.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.entities.Role;
import sptech.school.projetoPI.exceptions.exceptionClass.*;
import sptech.school.projetoPI.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService service;

    @Mock
    private EmployeeRepository repository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final Role role = Role.builder()
            .id(1)
            .name("OWNER")
            .build();

    private final Employee employee = Employee.builder()
            .id(1)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .active(true)
            .name("Miguel")
            .cpf("11122233345")
            .email("miguel@gmail.com")
            .phone("11989898989")
            .password("123456789")
            .cep("01234100")
            .role(role)
            .build();

    @Test
    @DisplayName("Quando método SignEmployee() for chamado com credenciais válidas, deve retornar Employee")
    void executeEmployeeSignWithValidParametersTest() {
        when(roleRepository.existsById(anyInt())).thenReturn(true);
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
        when(repository.existsByRoleName(anyString())).thenReturn(false);
        when(repository.save(employee)).thenReturn(employee);

        Employee response = service.signEmployee(employee);
        assertEquals(employee, response);
    }

    @Test
    @DisplayName("Quando não existir Role com ID requisitado, método SignEmployee() deve estourar RelatedEntityNotFoundException")
    void executeEmployeeSignWithInvalidRoleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(roleRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.signEmployee(employee));
    }

    @Test
    @DisplayName("Quando já existir Employee com Role OWNER, método SignEmployee() deve estourar EntityConflictException")
    void executeEmployeeSignWithRoleConflictMustThrowEntityConflictExceptionTest() {
        when(roleRepository.existsById(anyInt())).thenReturn(true);
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
        when(repository.existsByRoleName(anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.signEmployee(employee));
    }

    @Test
    @DisplayName("Quando existir 3 Employees na lista, método GetAllEmployees() deve retornar tamanho 3")
    void executeEmployeeFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<Employee> employees = List.of(
                Employee.builder()
                        .id(1)
                        .name("Fabricio")
                        .cpf("55522299915")
                        .email("fabricio@gmail.com")
                        .build(),

                Employee.builder()
                        .id(2)
                        .name("Miguel")
                        .cpf("11122233345")
                        .email("miguel@gmail.com")
                        .build(),

                Employee.builder()
                        .id(3)
                        .name("Martinez")
                        .cpf("88822244432")
                        .email("martinez@gmail.com")
                        .build()
        );

        when(repository.findAllByActiveTrue()).thenReturn(employees);

        List<Employee> response = service.getAllEmployees();
        assertEquals(3, response.size());
    }

    @Test
    @DisplayName("Quando existir Employee com ID 1, método GetEmployeeById() deve retornar o Employee encontrado")
    void executeEmployeeFindByIdMustReturnEmployeeWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(employee));

        Employee response = service.getEmployeeById(1);
        assertEquals(employee, response);
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado, método GetEmployeeById() deve estourar EntityNotFoundException")
    void executeEmployeeFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getEmployeeById(1));
    }

    @Test
    @DisplayName("Quando método UpdateEmployeeById() for chamado com credenciais válidas, deve retornar Employee atualizado")
    void executeEmployeeUpdateByIdWithValidEntityMustReturnUpdatedEmployeeTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(employee));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(roleRepository.existsById(anyInt())).thenReturn(true);
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
        when(repository.existsByIdNotAndRoleName(anyInt(), anyString())).thenReturn(false);
        when(repository.save(employee)).thenReturn(employee);

        Employee response = service.updateEmployeeById(employee, anyInt());
        assertEquals(employee, response);
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado, método UpdateEmployeeById() deve estourar EntityNotFoundException")
    void executeEmployeeUpdateByIdWithoutValidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.updateEmployeeById(employee, 1));
    }

    @Test
    @DisplayName("Quando Employee com ID requisitado estiver inativo, método UpdateEmployeeById() deve estourar InactiveEntityException")
    void executeEmployeeUpdateByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);

        assertThrows(InactiveEntityException.class, () -> service.updateEmployeeById(employee, 1));
    }

    @Test
    @DisplayName("Quando não existir Role com ID requisitado em Employee, método UpdateEmployeeById() deve estourar RelatedEntityNotFoundException")
    void executeEmployeeUpdateByIdWithInvalidRoleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(roleRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(RelatedEntityNotFoundException.class, () -> service.updateEmployeeById(employee, 1));
    }

    @Test
    @DisplayName("Quando Employee com ID requisitado possuir cargo OWNER já existente em outra entidade, método UpdateEmployeeById() deve estourar EntityConflictException")
    void executeEmployeeUpdateByIdWithRoleConflictMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(roleRepository.existsById(anyInt())).thenReturn(true);
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
        when(repository.existsByIdNotAndRoleName(anyInt(), anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.updateEmployeeById(employee, 1));
    }

    @Test
    @DisplayName("Quando método DeleteEmployeeById() for chamado com ID válido, deve inativar entidade")
    void executeEmployeeDeleteByIdWithValidIdMustInactiveEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(employee));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByEmployeeId(anyInt())).thenReturn(false);
        when(availabilityRepository.existsByEmployeeId(anyInt())).thenReturn(false);

        service.deleteEmployeeById(1);

        assertFalse(employee.getActive());
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado, método DeleteEmployeeById() deve estourar EntityNotFoundException")
    void executeEmployeeDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteEmployeeById(1));
    }

    @Test
    @DisplayName("Quando Employee com ID requisitado estiver inativo, método DeleteEmployeeById() deve estourar InactiveEntityException")
    void executeEmployeeDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteEmployeeById(1));
    }

    @Test
    @DisplayName("Quando Employee com ID requisitado estiver registrado em um Schedule, método DeleteEmployeeById() deve estourar ForeignKeyConstraintException")
    void executeEmployeeDeleteByIdWithScheduleForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByEmployeeId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteEmployeeById(1));
    }

    @Test
    @DisplayName("Quando Employee com ID requisitado estiver registrado em um Availability, método DeleteEmployeeById() deve estourar ForeignKeyConstraintException")
    void executeEmployeeDeleteByIdWithFeedbackForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByEmployeeId(anyInt())).thenReturn(false);
        when(availabilityRepository.existsByEmployeeId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteEmployeeById(1));
    }
}