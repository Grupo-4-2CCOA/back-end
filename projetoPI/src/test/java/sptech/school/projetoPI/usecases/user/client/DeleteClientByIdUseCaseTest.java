package sptech.school.projetoPI.usecases.user.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.application.usecases.user.client.DeleteClientByIdUseCase;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteClientByIdUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private ScheduleGateway scheduleGateway;

    @Mock
    private FeedbackGateway feedbackGateway;

    @InjectMocks
    private DeleteClientByIdUseCase deleteClientByIdUseCase;

    @Test
    @DisplayName("Deve inativar cliente com sucesso quando ID é válido")
    void deveInativarClienteComSucesso() {
        // Arrange
        Integer id = 1;
        RoleDomain role = new RoleDomain(2, true, LocalDateTime.now(), LocalDateTime.now(), "Cliente", "Descrição");
        UserDomain client = new UserDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Cliente Teste", "12345678900", "cliente@teste.com", "11999999999", "12345678", role);

        when(userGateway.existsById(id)).thenReturn(true);
        when(userGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(scheduleGateway.existsByClientId(id)).thenReturn(false);
        when(userGateway.findById(id)).thenReturn(Optional.of(client));
        when(userGateway.save(any(UserDomain.class))).thenReturn(client);

        // Act
        assertDoesNotThrow(() -> deleteClientByIdUseCase.execute(id));

        // Assert
        assertFalse(client.getActive());
        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(scheduleGateway, times(1)).existsByClientId(id);
        verify(userGateway, times(1)).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando cliente não existe")
    void deveLancarExcecaoQuandoClienteNaoExiste() {
        // Arrange
        Integer id = 999;
        when(userGateway.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            deleteClientByIdUseCase.execute(id);
        });

        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, never()).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar InactiveEntityException quando cliente já está inativo")
    void deveLancarExcecaoQuandoClienteJaEstaInativo() {
        // Arrange
        Integer id = 1;
        when(userGateway.existsById(id)).thenReturn(true);
        when(userGateway.existsByIdAndActiveFalse(id)).thenReturn(true);

        // Act & Assert
        assertThrows(InactiveEntityException.class, () -> {
            deleteClientByIdUseCase.execute(id);
        });

        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(userGateway, never()).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar ForeignKeyConstraintException quando cliente possui agendamentos relacionados")
    void deveLancarExcecaoQuandoClientePossuiAgendamentosRelacionados() {
        // Arrange
        Integer id = 1;
        when(userGateway.existsById(id)).thenReturn(true);
        when(userGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(scheduleGateway.existsByClientId(id)).thenReturn(true);

        // Act & Assert
        assertThrows(ForeignKeyConstraintException.class, () -> {
            deleteClientByIdUseCase.execute(id);
        });

        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(scheduleGateway, times(1)).existsByClientId(id);
        verify(userGateway, never()).save(any(UserDomain.class));
    }
}

