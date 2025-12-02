package sptech.school.projetoPI.usecases.user.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.user.client.GetClientByIdUseCase;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetClientByIdUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private GetClientByIdUseCase getClientByIdUseCase;

    @Test
    @DisplayName("Deve retornar cliente quando ID existe e está ativo")
    void deveRetornarClienteQuandoIdExiste() {
        // Arrange
        Integer id = 1;
        RoleDomain role = new RoleDomain(2, true, LocalDateTime.now(), LocalDateTime.now(), "Cliente", "Descrição");
        UserDomain client = new UserDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Cliente Teste", "12345678900", "cliente@teste.com", "11999999999", "12345678", role);

        when(userGateway.findByIdAndActiveTrue(id)).thenReturn(Optional.of(client));

        // Act
        UserDomain result = getClientByIdUseCase.execute(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Cliente Teste", result.getName());
        verify(userGateway, times(1)).findByIdAndActiveTrue(id);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        Integer id = 999;
        when(userGateway.findByIdAndActiveTrue(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            getClientByIdUseCase.execute(id);
        });

        verify(userGateway, times(1)).findByIdAndActiveTrue(id);
    }
}

