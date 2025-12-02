package sptech.school.projetoPI.usecases.user.employee;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.user.employee.GetAllEmployeeUseCase;
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
class GetAllEmployeeUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private GetAllEmployeeUseCase getAllEmployeeUseCase;

    @Test
    @DisplayName("Deve retornar lista de funcionários quando existem funcionários ativos")
    void deveRetornarListaDeFuncionarios() {
        // Arrange
        RoleDomain role = new RoleDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Funcionário", "Descrição");
        UserDomain employee1 = new UserDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Funcionário 1", "11111111111", "funcionario1@teste.com", "11999999999", "12345678", role);
        UserDomain employee2 = new UserDomain(2, true, LocalDateTime.now(), LocalDateTime.now(),
                "Funcionário 2", "22222222222", "funcionario2@teste.com", "11888888888", "87654321", role);

        List<UserDomain> employees = Arrays.asList(employee1, employee2);

        when(userGateway.findAllByActiveTrue()).thenReturn(employees);

        // Act
        List<UserDomain> result = getAllEmployeeUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Funcionário 1", result.get(0).getName());
        assertEquals("Funcionário 2", result.get(1).getName());
        verify(userGateway, times(1)).findAllByActiveTrue();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem funcionários ativos")
    void deveRetornarListaVazia() {
        // Arrange
        when(userGateway.findAllByActiveTrue()).thenReturn(Collections.emptyList());

        // Act
        List<UserDomain> result = getAllEmployeeUseCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userGateway, times(1)).findAllByActiveTrue();
    }
}

