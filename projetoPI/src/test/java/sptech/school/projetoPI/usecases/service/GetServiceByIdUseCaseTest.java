package sptech.school.projetoPI.usecases.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.service.GetServiceByIdUseCase;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetServiceByIdUseCaseTest {

    @Mock
    private ServiceGateway serviceGateway;

    @InjectMocks
    private GetServiceByIdUseCase getServiceByIdUseCase;

    @Test
    @DisplayName("Deve retornar serviço quando ID existe e está ativo")
    void deveRetornarServicoQuandoIdExiste() {
        // Arrange
        Integer id = 1;
        CategoryDomain category = new CategoryDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Categoria", "Descrição");
        ServiceDomain service = new ServiceDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Serviço Teste", 100.0, 60, "Descrição", "imagem.jpg", category);

        when(serviceGateway.findByIdAndActiveTrue(id)).thenReturn(Optional.of(service));

        // Act
        ServiceDomain result = getServiceByIdUseCase.execute(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Serviço Teste", result.getName());
        verify(serviceGateway, times(1)).findByIdAndActiveTrue(id);
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando serviço não existe")
    void deveLancarExcecaoQuandoServicoNaoExiste() {
        // Arrange
        Integer id = 999;
        when(serviceGateway.findByIdAndActiveTrue(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            getServiceByIdUseCase.execute(id);
        });

        verify(serviceGateway, times(1)).findByIdAndActiveTrue(id);
    }
}

