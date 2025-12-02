package sptech.school.projetoPI.usecases.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.service.GetAllServiceUseCase;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllServiceUseCaseTest {

    @Mock
    private ServiceGateway serviceGateway;

    @InjectMocks
    private GetAllServiceUseCase getAllServiceUseCase;

    @Test
    @DisplayName("Deve retornar lista de serviços quando existem serviços ativos")
    void deveRetornarListaDeServicos() {
        // Arrange
        CategoryDomain category = new CategoryDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Categoria", "Descrição");
        ServiceDomain service1 = new ServiceDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Serviço 1", 100.0, 60, "Descrição 1", "imagem1.jpg", category);
        ServiceDomain service2 = new ServiceDomain(2, true, LocalDateTime.now(), LocalDateTime.now(),
                "Serviço 2", 200.0, 90, "Descrição 2", "imagem2.jpg", category);

        List<ServiceDomain> services = Arrays.asList(service1, service2);

        when(serviceGateway.findAllByActiveTrue()).thenReturn(services);

        // Act
        List<ServiceDomain> result = getAllServiceUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Serviço 1", result.get(0).getName());
        assertEquals("Serviço 2", result.get(1).getName());
        verify(serviceGateway, times(1)).findAllByActiveTrue();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem serviços ativos")
    void deveRetornarListaVazia() {
        // Arrange
        when(serviceGateway.findAllByActiveTrue()).thenReturn(Collections.emptyList());

        // Act
        List<ServiceDomain> result = getAllServiceUseCase.execute();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(serviceGateway, times(1)).findAllByActiveTrue();
    }
}

