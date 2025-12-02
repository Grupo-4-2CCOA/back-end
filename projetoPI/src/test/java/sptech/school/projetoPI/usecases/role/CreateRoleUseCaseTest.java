package sptech.school.projetoPI.usecases.role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.role.CreateRoleUseCase;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.gateways.RoleGateway;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRoleUseCaseTest {

    @Mock
    private RoleGateway roleGateway;

    @InjectMocks
    private CreateRoleUseCase createRoleUseCase;

    @Test
    @DisplayName("Deve criar role com sucesso quando dados são válidos")
    void deveCriarRoleComSucesso() {
        // Arrange
        RoleDomain role = new RoleDomain();
        role.setName("Role Teste");
        role.setDescription("Descrição");
        role.setActive(true);

        RoleDomain savedRole = new RoleDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Role Teste", "Descrição");

        when(roleGateway.existsByName(anyString())).thenReturn(false);
        when(roleGateway.save(any(RoleDomain.class))).thenReturn(savedRole);

        // Act
        RoleDomain result = createRoleUseCase.execute(role);

        // Assert
        assertNotNull(result);
        assertEquals(savedRole.getId(), result.getId());
        verify(roleGateway, times(1)).existsByName(anyString());
        verify(roleGateway, times(1)).save(any(RoleDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityConflictException quando role com mesmo nome já existe")
    void deveLancarExcecaoQuandoRoleJaExiste() {
        // Arrange
        RoleDomain role = new RoleDomain();
        role.setName("Role Existente");

        when(roleGateway.existsByName(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(EntityConflictException.class, () -> {
            createRoleUseCase.execute(role);
        });

        verify(roleGateway, times(1)).existsByName(anyString());
        verify(roleGateway, never()).save(any(RoleDomain.class));
    }
}

