package sptech.school.projetoPI.usecases.user.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.application.usecases.user.client.UpdateClientByIdUseCase;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateClientByIdUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private UpdateClientByIdUseCase updateClientByIdUseCase;

    @Test
    @DisplayName("Deve atualizar cliente com sucesso quando dados são válidos")
    void deveAtualizarClienteComSucesso() {
        // Arrange
        Integer id = 1;
        RoleDomain role = new RoleDomain(2, true, LocalDateTime.now(), LocalDateTime.now(), "Cliente", "Descrição");
        UserDomain existingClient = new UserDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Cliente Antigo", "11111111111", "antigo@teste.com", "11999999999", "12345678", role);

        UserDomain updatedClient = new UserDomain();
        updatedClient.setName("Cliente Atualizado");
        updatedClient.setCpf("22222222222");
        updatedClient.setEmail("novo@teste.com");
        updatedClient.setPhone("11888888888");

        UserDomain savedClient = new UserDomain(id, true, existingClient.getCreatedAt(), LocalDateTime.now(),
                "Cliente Atualizado", "22222222222", "novo@teste.com", "11888888888", "12345678", role);

        when(userGateway.existsById(id)).thenReturn(true);
        when(userGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(userGateway.existsByIdNotAndCpf(id, updatedClient.getCpf())).thenReturn(false);
        when(userGateway.existsByIdNotAndEmailIgnoreCase(id, updatedClient.getEmail())).thenReturn(false);
        when(userGateway.existsByIdNotAndPhone(id, updatedClient.getPhone())).thenReturn(false);
        when(userGateway.findById(id)).thenReturn(Optional.of(existingClient));
        when(userGateway.save(any(UserDomain.class))).thenReturn(savedClient);

        // Act
        UserDomain result = updateClientByIdUseCase.execute(updatedClient, id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Cliente Atualizado", result.getName());
        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, times(1)).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        Integer id = 999;
        UserDomain client = new UserDomain();
        client.setName("Cliente Teste");

        when(userGateway.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            updateClientByIdUseCase.execute(client, id);
        });

        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, never()).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar InactiveEntityException quando cliente está inativo")
    void deveLancarExcecaoQuandoClienteEstaInativo() {
        // Arrange
        Integer id = 1;
        UserDomain client = new UserDomain();
        client.setName("Cliente Teste");

        when(userGateway.existsById(id)).thenReturn(true);
        when(userGateway.existsByIdAndActiveFalse(id)).thenReturn(true);

        // Act & Assert
        assertThrows(InactiveEntityException.class, () -> {
            updateClientByIdUseCase.execute(client, id);
        });

        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(userGateway, never()).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar ConflictException quando CPF já existe em outro cliente")
    void deveLancarExcecaoQuandoCpfJaExiste() {
        // Arrange
        Integer id = 1;
        UserDomain client = new UserDomain();
        client.setCpf("12345678900");

        when(userGateway.existsById(id)).thenReturn(true);
        when(userGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(userGateway.existsByIdNotAndCpf(id, client.getCpf())).thenReturn(true);

        // Act & Assert
        assertThrows(ConflictException.class, () -> {
            updateClientByIdUseCase.execute(client, id);
        });

        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(userGateway, times(1)).existsByIdNotAndCpf(id, client.getCpf());
        verify(userGateway, never()).save(any(UserDomain.class));
    }
}

