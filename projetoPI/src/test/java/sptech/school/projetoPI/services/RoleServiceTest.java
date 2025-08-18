package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.EmployeeRepository;
import sptech.school.projetoPI.repositories.RoleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class RoleServiceTest extends ServiceTest {

    @InjectMocks
    private RoleService service;

    @Mock
    private RoleRepository repository;

    @Mock
    private EmployeeRepository employeeRepository;

    private final Role role = Role.builder()
            .id(1)
            .name("OWNER")
            .description("Dono(a) do Salão de Beleza")
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    @Override
    @Test
    @DisplayName("Quando método postMethod() for chamado com credenciais válidas, deve retornar Role")
    void executeEntitySignWithValidParametersTest() {
        when(repository.existsByName(anyString())).thenReturn(false);
        when(repository.save(role)).thenReturn(role);

        Role response = service.postMethod(role);
        assertEquals(role, response);
    }

    @Test
    @DisplayName("Quando já existir Role com o mesmo nome, método postMethod() deve estourar EntityConflictException")
    void executeRoleSignWithExistingRoleNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsByName(anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.postMethod(role));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Roles na lista, método getAllMethod() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<Role> roles = List.of(
                Role.builder()
                        .id(1)
                        .name("OWNER")
                        .build(),

                Role.builder()
                        .id(2)
                        .name("EMPLOYEE")
                        .build(),

                Role.builder()
                        .id(3)
                        .name("ADMIN")
                        .build()
        );

        when(repository.findAllByActiveTrue()).thenReturn(roles);

        List<Role> response = service.getAllMethod();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Role com ID 1, método getByIdMethod() deve retornar o Role encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(role));

        Role response = service.getByIdMethod(1);
        assertEquals(role, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Role com ID requisitado, método getByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getByIdMethod(1));
    }

    @Override
    @Test
    @DisplayName("Quando método putByIdMethod() for chamado com credenciais válidas, deve retornar Role atualizado")
    void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Role()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(false);
        when(repository.save(role)).thenReturn(role);

        Role response = service.putByIdMethod(role, anyInt());
        assertEquals(role, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Role com ID requisitado, método putByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.putByIdMethod(role, 1));
    }

    @Test
    @DisplayName("Quando Role com ID requisitado estiver inativo, método putByIdMethod() deve estourar InactiveEntityException")
    void executeRolePutByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.putByIdMethod(role, 1));
    }

    @Test
    @DisplayName("Quando nome de Role já estiver registrado, método putByIdMethod() deve estourar EntityConflictException")
    void executeRolePutByIdWithExistingRoleNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.putByIdMethod(role, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método deleteByIdMethod() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(role));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(employeeRepository.existsByRoleId(anyInt())).thenReturn(false);

        service.deleteByIdMethod(1);

        assertFalse(role.getActive());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Role com ID requisitado, método deleteByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Role com ID requisitado estiver inativo, método deleteByIdMethod() deve estourar InactiveEntityException")
    void executeRoleDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Role com ID requisitado estiver registrado em um Schedule, método deleteByIdMethod() deve estourar ForeignKeyConstraintException")
    void executeRoleDeleteByIdWithScheduleForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(employeeRepository.existsByRoleId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteByIdMethod(1));
    }
}