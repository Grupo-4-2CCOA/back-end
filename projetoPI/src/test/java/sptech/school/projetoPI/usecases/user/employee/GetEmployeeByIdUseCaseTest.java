package sptech.school.projetoPI.usecases.user.employee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.user.employee.GetEmployeeByIdUseCase;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetEmployeeByIdUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private GetEmployeeByIdUseCase getEmployeeByIdUseCase;

    @Test
    @DisplayName("Deve retornar funcionário quando ID existe e está ativo")
    void deveRetornarFuncionarioQuandoIdExiste() {
        // Arrange
        Integer id = 1;
        RoleDomain role = new RoleDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Funcionário", "Descrição");
        UserDomain employee = new UserDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Funcionário Teste", "12345678900", "funcionario@teste.com", "11999999999", "12345678", role);

        when(userGateway.findByIdAndActiveTrue(id)).thenReturn(Optional.of(employee));

        // Act
        UserDomain result = getEmployeeByIdUseCase.execute(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Funcionário Teste", result.getName());
        verify(userGateway, times(1)).findByIdAndActiveTrue(id);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando funcionário não existe")
    void deveLancarExcecaoQuandoFuncionarioNaoExiste() {
        // Arrange
        Integer id = 999;
        when(userGateway.findByIdAndActiveTrue(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            getEmployeeByIdUseCase.execute(id);
        });

        verify(userGateway, times(1)).findByIdAndActiveTrue(id);
    }
}

