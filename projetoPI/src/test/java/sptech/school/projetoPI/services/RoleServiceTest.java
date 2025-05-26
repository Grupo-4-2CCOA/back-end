package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.entities.Role;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.EmployeeRepository;
import sptech.school.projetoPI.repositories.RoleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

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


    @Test
    @DisplayName("Quando método SignRole() for chamado com credenciais válidas, deve retornar Role")
    void executeRoleSignWithValidParametersTest() {
        when(repository.existsByName(anyString())).thenReturn(false);
        when(repository.save(role)).thenReturn(role);

        Role response = service.signRole(role);
        assertEquals(role, response);
    }

    @Test
    @DisplayName("Quando já existir Role com o mesmo nome, método SignRole() deve estourar EntityConflictException")
    void executeRoleSignWithExistingRoleNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsByName(anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.signRole(role));
    }

    @Test
    @DisplayName("Quando existir 3 Roles na lista, método GetAllRoles() deve retornar tamanho 3")
    void executeRoleFindAllWithThreeEntitiesMustReturnThreeTest() {
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

        List<Role> response = service.getAllRoles();
        assertEquals(3, response.size());
    }

    @Test
    @DisplayName("Quando existir Role com ID 1, método GetRoleById() deve retornar o Role encontrado")
    void executeRoleFindByIdMustReturnRoleWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(role));

        Role response = service.getRoleById(1);
        assertEquals(role, response);
    }

    @Test
    @DisplayName("Quando não existir Role com ID requisitado, método GetRoleById() deve estourar EntityNotFoundException")
    void executeRoleFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getRoleById(1));
    }

    @Test
    @DisplayName("Quando método UpdateRoleById() for chamado com credenciais válidas, deve retornar Role atualizado")
    void executeRoleUpdateByIdWithValidEntityMustReturnUpdatedRoleTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Role()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(false);
        when(repository.save(role)).thenReturn(role);

        Role response = service.updateRoleById(role, anyInt());
        assertEquals(role, response);
    }

    @Test
    @DisplayName("Quando não existir Role com ID requisitado, método UpdateRoleById() deve estourar EntityNotFoundException")
    void executeRoleUpdateByIdWithoutValidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.updateRoleById(role, 1));
    }

    @Test
    @DisplayName("Quando Role com ID requisitado estiver inativo, método UpdateRoleById() deve estourar InactiveEntityException")
    void executeRoleUpdateByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.updateRoleById(role, 1));
    }

    @Test
    @DisplayName("Quando nome de Role já estiver registrado, método UpdateRoleById() deve estourar EntityConflictException")
    void executeRoleUpdateByIdWithExistingRoleNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndName(anyInt(), anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.updateRoleById(role, 1));
    }

    @Test
    @DisplayName("Quando método DeleteRoleById() for chamado com ID válido, deve inativar entidade")
    void executeRoleDeleteByIdWithValidIdMustInactiveEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(role));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(employeeRepository.existsByRoleId(anyInt())).thenReturn(false);

        service.deleteRoleById(1);

        assertFalse(role.getActive());
    }

    @Test
    @DisplayName("Quando não existir Role com ID requisitado, método DeleteRoleById() deve estourar EntityNotFoundException")
    void executeRoleDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteRoleById(1));
    }

    @Test
    @DisplayName("Quando Role com ID requisitado estiver inativo, método DeleteRoleById() deve estourar InactiveEntityException")
    void executeRoleDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteRoleById(1));
    }

    @Test
    @DisplayName("Quando Role com ID requisitado estiver registrado em um Schedule, método DeleteRoleById() deve estourar ForeignKeyConstraintException")
    void executeRoleDeleteByIdWithScheduleForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(employeeRepository.existsByRoleId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteRoleById(1));
    }
}