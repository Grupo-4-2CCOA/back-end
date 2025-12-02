package sptech.school.projetoPI.usecases.user.employee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.core.application.usecases.user.employee.CreateEmployeeUseCase;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateEmployeeUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private RoleGateway roleGateway;

    @InjectMocks
    private CreateEmployeeUseCase createEmployeeUseCase;

    @Test
    @DisplayName("Deve criar funcionário com sucesso quando dados são válidos")
    void deveCriarFuncionarioComSucesso() {
        // Arrange
        RoleDomain role = new RoleDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Funcionário", "Descrição");
        UserDomain user = new UserDomain();
        user.setName("Funcionário Teste");
        user.setCpf("12345678900");
        user.setEmail("funcionario@teste.com");
        user.setPhone("11999999999");
        user.setCep("12345678");
        user.setActive(true);

        UserDomain savedUser = new UserDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Funcionário Teste", "12345678900", "funcionario@teste.com", "11999999999", "12345678", role);

        when(userGateway.existsByCpf(anyString())).thenReturn(false);
        when(userGateway.existsByEmailIgnoreCase(anyString())).thenReturn(false);
        when(userGateway.existsByPhone(anyString())).thenReturn(false);
        when(roleGateway.findById(1)).thenReturn(Optional.of(role));
        when(userGateway.save(any(UserDomain.class))).thenReturn(savedUser);

        // Act
        UserDomain result = createEmployeeUseCase.execute(user);

        // Assert
        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getId());
        assertEquals(savedUser.getName(), result.getName());
        verify(userGateway, times(1)).existsByCpf(anyString());
        verify(userGateway, times(1)).existsByEmailIgnoreCase(anyString());
        verify(userGateway, times(1)).existsByPhone(anyString());
        verify(roleGateway, times(1)).findById(1);
        verify(userGateway, times(1)).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar ConflictException quando CPF já existe")
    void deveLancarExcecaoQuandoCpfJaExiste() {
        // Arrange
        UserDomain user = new UserDomain();
        user.setCpf("12345678900");

        when(userGateway.existsByCpf(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> {
            createEmployeeUseCase.execute(user);
        });

        verify(userGateway, times(1)).existsByCpf(anyString());
        verify(userGateway, never()).save(any(UserDomain.class));
    }
}

