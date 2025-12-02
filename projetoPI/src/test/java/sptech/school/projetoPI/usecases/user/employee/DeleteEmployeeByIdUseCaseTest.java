package sptech.school.projetoPI.usecases.user.employee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.application.usecases.user.employee.DeleteEmployeeByIdUseCase;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteEmployeeByIdUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private ScheduleGateway scheduleGateway;

    @Mock
    private AvailabilityGateway availabilityGateway;

    @InjectMocks
    private DeleteEmployeeByIdUseCase deleteEmployeeByIdUseCase;

    @Test
    @DisplayName("Deve inativar funcionário com sucesso quando ID é válido")
    void deveInativarFuncionarioComSucesso() {
        // Arrange
        Integer id = 1;
        RoleDomain role = new RoleDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Funcionário", "Descrição");
        UserDomain employee = new UserDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Funcionário Teste", "12345678900", "funcionario@teste.com", "11999999999", "12345678", role);

        when(userGateway.existsById(id)).thenReturn(true);
        when(userGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(scheduleGateway.existsByEmployeeId(id)).thenReturn(false);
        when(availabilityGateway.existsByEmployeeId(id)).thenReturn(false);
        when(userGateway.findById(id)).thenReturn(Optional.of(employee));
        when(userGateway.save(any(UserDomain.class))).thenReturn(employee);

        // Act
        assertDoesNotThrow(() -> deleteEmployeeByIdUseCase.execute(id));

        // Assert
        assertFalse(employee.getActive());
        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(scheduleGateway, times(1)).existsByEmployeeId(id);
        verify(availabilityGateway, times(1)).existsByEmployeeId(id);
        verify(userGateway, times(1)).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando funcionário não existe")
    void deveLancarExcecaoQuandoFuncionarioNaoExiste() {
        // Arrange
        Integer id = 999;
        when(userGateway.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            deleteEmployeeByIdUseCase.execute(id);
        });

        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, never()).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar ForeignKeyConstraintException quando funcionário possui agendamentos relacionados")
    void deveLancarExcecaoQuandoFuncionarioPossuiAgendamentosRelacionados() {
        // Arrange
        Integer id = 1;
        when(userGateway.existsById(id)).thenReturn(true);
        when(userGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(scheduleGateway.existsByEmployeeId(id)).thenReturn(true);

        // Act & Assert
        assertThrows(ForeignKeyConstraintException.class, () -> {
            deleteEmployeeByIdUseCase.execute(id);
        });

        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(scheduleGateway, times(1)).existsByEmployeeId(id);
        verify(userGateway, never()).save(any(UserDomain.class));
    }
}

