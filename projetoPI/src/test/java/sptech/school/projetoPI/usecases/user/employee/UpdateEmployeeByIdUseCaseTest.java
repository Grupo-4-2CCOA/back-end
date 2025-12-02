package sptech.school.projetoPI.usecases.user.employee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.user.employee.UpdateEmployeeByIdUseCase;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateEmployeeByIdUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private RoleGateway roleGateway;

    @InjectMocks
    private UpdateEmployeeByIdUseCase updateEmployeeByIdUseCase;

    @Test
    @DisplayName("Deve atualizar funcionário com sucesso quando dados são válidos")
    void deveAtualizarFuncionarioComSucesso() {
        // Arrange
        Integer id = 1;
        RoleDomain role = new RoleDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Funcionário", "Descrição");
        UserDomain existingEmployee = new UserDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Funcionário Antigo", "11111111111", "antigo@teste.com", "11999999999", "12345678", role);

        UserDomain updatedEmployee = new UserDomain();
        updatedEmployee.setName("Funcionário Atualizado");
        updatedEmployee.setCpf("22222222222");
        updatedEmployee.setEmail("novo@teste.com");
        updatedEmployee.setPhone("11888888888");
        updatedEmployee.setRoleDomain(role);

        UserDomain savedEmployee = new UserDomain(id, true, existingEmployee.getCreatedAt(), LocalDateTime.now(),
                "Funcionário Atualizado", "22222222222", "novo@teste.com", "11888888888", "12345678", role);

        when(userGateway.existsById(id)).thenReturn(true);
        when(userGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(roleGateway.existsById(role.getId())).thenReturn(true);
        when(roleGateway.findById(role.getId())).thenReturn(Optional.of(role));
        when(userGateway.existsByIdNotAndCpf(id, updatedEmployee.getCpf())).thenReturn(false);
        when(userGateway.existsByIdNotAndEmailIgnoreCase(id, updatedEmployee.getEmail())).thenReturn(false);
        when(userGateway.existsByIdNotAndPhone(id, updatedEmployee.getPhone())).thenReturn(false);
        when(userGateway.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(userGateway.save(any(UserDomain.class))).thenReturn(savedEmployee);

        // Act
        UserDomain result = updateEmployeeByIdUseCase.execute(updatedEmployee, id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Funcionário Atualizado", result.getName());
        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, times(1)).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando funcionário não existe")
    void deveLancarExcecaoQuandoFuncionarioNaoExiste() {
        // Arrange
        Integer id = 999;
        UserDomain employee = new UserDomain();
        employee.setName("Funcionário Teste");

        when(userGateway.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            updateEmployeeByIdUseCase.execute(employee, id);
        });

        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, never()).save(any(UserDomain.class));
    }

    @Test
    @DisplayName("Deve lançar RelatedEntityNotFoundException quando role não existe")
    void deveLancarExcecaoQuandoRoleNaoExiste() {
        // Arrange
        Integer id = 1;
        RoleDomain role = new RoleDomain(999, true, LocalDateTime.now(), LocalDateTime.now(), "Role", "Descrição");
        UserDomain employee = new UserDomain();
        employee.setName("Funcionário Teste");
        employee.setCpf("12345678900");
        employee.setEmail("funcionario@teste.com");
        employee.setPhone("11999999999");
        employee.setRoleDomain(role);

        when(userGateway.existsById(id)).thenReturn(true);
        when(userGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(roleGateway.existsById(role.getId())).thenReturn(false);

        // Act & Assert
        assertThrows(RelatedEntityNotFoundException.class, () -> {
            updateEmployeeByIdUseCase.execute(employee, id);
        });

        verify(userGateway, times(1)).existsById(id);
        verify(userGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(roleGateway, times(1)).existsById(role.getId());
        verify(userGateway, never()).save(any(UserDomain.class));
    }
}

