package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.*;
import sptech.school.projetoPI.core.domains.EmployeeDomain;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.repositories.*;
import sptech.school.projetoPI.services.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class EmployeeServiceTest extends ServiceTest {

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

    private final RoleDomain roleDomain = RoleDomain.builder()
            .id(1)
            .name("OWNER")
            .build();

    private final EmployeeDomain employeeDomain = EmployeeDomain.builder()
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
            .role(roleDomain)
            .build();

    @Override
    @Test
    @DisplayName("Quando método postMethod() for chamado com credenciais válidas, deve retornar Employee")
    void executeEntitySignWithValidParametersTest() {
        when(roleRepository.existsById(anyInt())).thenReturn(true);
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(roleDomain));
        when(repository.existsByRoleName(anyString())).thenReturn(false);
        when(repository.save(employeeDomain)).thenReturn(employeeDomain);

        EmployeeDomain response = service.postMethod(employeeDomain);
        assertEquals(employeeDomain, response);
    }

    @Test
    @DisplayName("Quando não existir Role com ID requisitado, método postMethod() deve estourar RelatedEntityNotFoundException")
    void executeEmployeeSignWithInvalidRoleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(roleRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.postMethod(employeeDomain));
    }

    @Test
    @DisplayName("Quando já existir Employee com Role OWNER, método postMethod() deve estourar EntityConflictException")
    void executeEmployeeSignWithRoleConflictMustThrowEntityConflictExceptionTest() {
        when(roleRepository.existsById(anyInt())).thenReturn(true);
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(roleDomain));
        when(repository.existsByRoleName(anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.postMethod(employeeDomain));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Employees na lista, método getAllMethod() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<EmployeeDomain> employeeDomains = List.of(
                EmployeeDomain.builder()
                        .id(1)
                        .name("Fabricio")
                        .cpf("55522299915")
                        .email("fabricio@gmail.com")
                        .build(),

                EmployeeDomain.builder()
                        .id(2)
                        .name("Miguel")
                        .cpf("11122233345")
                        .email("miguel@gmail.com")
                        .build(),

                EmployeeDomain.builder()
                        .id(3)
                        .name("Martinez")
                        .cpf("88822244432")
                        .email("martinez@gmail.com")
                        .build()
        );

        when(repository.findAllByActiveTrue()).thenReturn(employeeDomains);

        List<EmployeeDomain> response = service.getAllMethod();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Employee com ID 1, método getByIdMethod() deve retornar o Employee encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(employeeDomain));

        EmployeeDomain response = service.getByIdMethod(1);
        assertEquals(employeeDomain, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Employee com ID requisitado, método getByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getByIdMethod(1));
    }

    @Override
    @Test
    @DisplayName("Quando método putByIdMethod() for chamado com credenciais válidas, deve retornar Employee atualizado")
    void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(employeeDomain));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(roleRepository.existsById(anyInt())).thenReturn(true);
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(roleDomain));
        when(repository.existsByIdNotAndRoleName(anyInt(), anyString())).thenReturn(false);
        when(repository.save(employeeDomain)).thenReturn(employeeDomain);

        EmployeeDomain response = service.putByIdMethod(employeeDomain, anyInt());
        assertEquals(employeeDomain, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Employee com ID requisitado, método putByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.putByIdMethod(employeeDomain, 1));
    }

    @Test
    @DisplayName("Quando Employee com ID requisitado estiver inativo, método putByIdMethod() deve estourar InactiveEntityException")
    void executeEmployeePutByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);

        assertThrows(InactiveEntityException.class, () -> service.putByIdMethod(employeeDomain, 1));
    }

    @Test
    @DisplayName("Quando não existir Role com ID requisitado em Employee, método putByIdMethod() deve estourar RelatedEntityNotFoundException")
    void executeEmployeePutByIdWithInvalidRoleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(roleRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(RelatedEntityNotFoundException.class, () -> service.putByIdMethod(employeeDomain, 1));
    }

    @Test
    @DisplayName("Quando Employee com ID requisitado possuir cargo OWNER já existente em outra entidade, método putByIdMethod() deve estourar EntityConflictException")
    void executeEmployeePutByIdWithRoleConflictMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(roleRepository.existsById(anyInt())).thenReturn(true);
        when(roleRepository.findById(anyInt())).thenReturn(Optional.of(roleDomain));
        when(repository.existsByIdNotAndRoleName(anyInt(), anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.putByIdMethod(employeeDomain, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método deleteByIdMethod() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(employeeDomain));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByEmployeeId(anyInt())).thenReturn(false);
        when(availabilityRepository.existsByEmployeeId(anyInt())).thenReturn(false);

        service.deleteByIdMethod(1);

        assertFalse(employeeDomain.getActive());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Employee com ID requisitado, método deleteByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Employee com ID requisitado estiver inativo, método deleteByIdMethod() deve estourar InactiveEntityException")
    void executeEmployeeDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Employee com ID requisitado estiver registrado em um Schedule, método deleteByIdMethod() deve estourar ForeignKeyConstraintException")
    void executeEmployeeDeleteByIdWithScheduleForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByEmployeeId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Employee com ID requisitado estiver registrado em um Availability, método deleteByIdMethod() deve estourar ForeignKeyConstraintException")
    void executeEmployeeDeleteByIdWithFeedbackForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByEmployeeId(anyInt())).thenReturn(false);
        when(availabilityRepository.existsByEmployeeId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteByIdMethod(1));
    }
}