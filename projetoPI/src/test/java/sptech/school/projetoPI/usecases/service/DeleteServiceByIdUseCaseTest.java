package sptech.school.projetoPI.usecases.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.application.usecases.service.DeleteServiceByIdUseCase;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteServiceByIdUseCaseTest {

    @Mock
    private ServiceGateway serviceGateway;

    @InjectMocks
    private DeleteServiceByIdUseCase deleteServiceByIdUseCase;

    @Test
    @DisplayName("Deve inativar serviço com sucesso quando ID é válido")
    void deveInativarServicoComSucesso() {
        // Arrange
        Integer id = 1;
        CategoryDomain category = new CategoryDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Categoria", "Descrição");
        ServiceDomain service = new ServiceDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Serviço Teste", 100.0, 60, "Descrição", "imagem.jpg", category);

        when(serviceGateway.existsById(id)).thenReturn(true);
        when(serviceGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(serviceGateway.findById(id)).thenReturn(Optional.of(service));
        when(serviceGateway.save(any(ServiceDomain.class))).thenReturn(service);

        // Act
        assertDoesNotThrow(() -> deleteServiceByIdUseCase.execute(id));

        // Assert
        assertFalse(service.getActive());
        verify(serviceGateway, times(1)).existsById(id);
        verify(serviceGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(serviceGateway, times(1)).findById(id);
        verify(serviceGateway, times(1)).save(any(ServiceDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando serviço não existe")
    void deveLancarExcecaoQuandoServicoNaoExiste() {
        // Arrange
        Integer id = 999;
        when(serviceGateway.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            deleteServiceByIdUseCase.execute(id);
        });

        verify(serviceGateway, times(1)).existsById(id);
        verify(serviceGateway, never()).save(any(ServiceDomain.class));
    }

    @Test
    @DisplayName("Deve lançar InactiveEntityException quando serviço já está inativo")
    void deveLancarExcecaoQuandoServicoJaEstaInativo() {
        // Arrange
        Integer id = 1;
        when(serviceGateway.existsById(id)).thenReturn(true);
        when(serviceGateway.existsByIdAndActiveFalse(id)).thenReturn(true);

        // Act & Assert
        assertThrows(InactiveEntityException.class, () -> {
            deleteServiceByIdUseCase.execute(id);
        });

        verify(serviceGateway, times(1)).existsById(id);
        verify(serviceGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(serviceGateway, never()).save(any(ServiceDomain.class));
    }
}

