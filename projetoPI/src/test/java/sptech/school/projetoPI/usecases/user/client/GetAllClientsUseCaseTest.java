package sptech.school.projetoPI.usecases.user.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.user.client.GetAllClientsUseCase;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllClientsUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private GetAllClientsUseCase getAllClientsUseCase;

    @Test
    @DisplayName("Deve retornar lista de clientes quando existem clientes ativos")
    void deveRetornarListaDeClientes() {
        // Arrange
        RoleDomain role = new RoleDomain(2, true, LocalDateTime.now(), LocalDateTime.now(), "Cliente", "Descrição");
        UserDomain client1 = new UserDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Cliente 1", "11111111111", "cliente1@teste.com", "11999999999", "12345678", role);
        UserDomain client2 = new UserDomain(2, true, LocalDateTime.now(), LocalDateTime.now(),
                "Cliente 2", "22222222222", "cliente2@teste.com", "11888888888", "87654321", role);

        List<UserDomain> clients = Arrays.asList(client1, client2);

        when(userGateway.findAllByActiveTrue()).thenReturn(clients);

        // Act
        List<UserDomain> result = getAllClientsUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Cliente 1", result.get(0).getName());
        assertEquals("Cliente 2", result.get(1).getName());
        verify(userGateway, times(1)).findAllByActiveTrue();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem clientes ativos")
    void deveRetornarListaVazia() {
        // Arrange
        when(userGateway.findAllByActiveTrue()).thenReturn(Collections.emptyList());

        // Act
        List<UserDomain> result = getAllClientsUseCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userGateway, times(1)).findAllByActiveTrue();
    }
}

