package sptech.school.projetoPI.usecases.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.*;
import sptech.school.projetoPI.core.application.usecases.service.UpdateServiceByIdUseCase;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.FileUploadGateway;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateServiceByIdUseCaseTest {

    @Mock
    private ServiceGateway serviceGateway;

    @Mock
    private FileUploadGateway fileUploadGateway;

    @InjectMocks
    private UpdateServiceByIdUseCase updateServiceByIdUseCase;

    @Test
    @DisplayName("Deve atualizar serviço com sucesso quando dados são válidos")
    void deveAtualizarServicoComSucesso() {
        // Arrange
        Integer id = 1;
        CategoryDomain category = new CategoryDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Categoria", "Descrição");
        ServiceDomain existingService = new ServiceDomain(id, true, LocalDateTime.now(), LocalDateTime.now(),
                "Serviço Antigo", 100.0, 60, "Descrição Antiga", "imagem-antiga.jpg", category);

        ServiceDomain updatedService = new ServiceDomain();
        updatedService.setName("Serviço Atualizado");
        updatedService.setDescription("Nova Descrição");
        updatedService.setBasePrice(150.0);
        updatedService.setBaseDuration(90);
        updatedService.setCategory(category);

        ServiceDomain savedService = new ServiceDomain(id, true, existingService.getCreatedAt(), LocalDateTime.now(),
                "Serviço Atualizado", 150.0, 90, "Nova Descrição", "imagem-nova.jpg", category);

        // Mock para ambas as chamadas de existsById (linha 24 e linha 45 do UseCase)
        when(serviceGateway.existsById(id)).thenReturn(true);
        when(serviceGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(serviceGateway.existsByIdNotAndName(id, updatedService.getName())).thenReturn(false);
        when(serviceGateway.findById(id)).thenReturn(Optional.of(existingService));
        when(fileUploadGateway.uploadFile(anyString(), any(byte[].class))).thenReturn("url-da-imagem");
        when(serviceGateway.save(any(ServiceDomain.class))).thenReturn(savedService);

        // Act
        ServiceDomain result = updateServiceByIdUseCase.execute(updatedService, id, "imagem-nova.jpg".getBytes(), "imagem-nova.jpg");

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Serviço Atualizado", result.getName());
        // existsById é chamado duas vezes: linha 24 e linha 45 do UseCase
        verify(serviceGateway, atLeast(1)).existsById(id);
        verify(serviceGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(serviceGateway, times(1)).existsByIdNotAndName(id, updatedService.getName());
        verify(fileUploadGateway, times(1)).uploadFile(anyString(), any(byte[].class));
        verify(serviceGateway, times(1)).save(any(ServiceDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityNotFoundException quando serviço não existe")
    void deveLancarExcecaoQuandoServicoNaoExiste() {
        // Arrange
        Integer id = 999;
        ServiceDomain service = new ServiceDomain();
        service.setName("Serviço Teste");

        when(serviceGateway.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            updateServiceByIdUseCase.execute(service, id, "imagem.jpg".getBytes(), "imagem.jpg");
        });

        verify(serviceGateway, times(1)).existsById(id);
        verify(serviceGateway, never()).save(any(ServiceDomain.class));
    }

    @Test
    @DisplayName("Deve lançar InactiveEntityException quando serviço está inativo")
    void deveLancarExcecaoQuandoServicoEstaInativo() {
        // Arrange
        Integer id = 1;
        ServiceDomain service = new ServiceDomain();
        service.setName("Serviço Teste");

        when(serviceGateway.existsById(id)).thenReturn(true);
        when(serviceGateway.existsByIdAndActiveFalse(id)).thenReturn(true);

        // Act & Assert
        assertThrows(InactiveEntityException.class, () -> {
            updateServiceByIdUseCase.execute(service, id, "imagem.jpg".getBytes(), "imagem.jpg");
        });

        verify(serviceGateway, times(1)).existsById(id);
        verify(serviceGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(serviceGateway, never()).save(any(ServiceDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityConflictException quando nome já existe em outro serviço")
    void deveLancarExcecaoQuandoNomeJaExiste() {
        // Arrange
        Integer id = 1;
        ServiceDomain service = new ServiceDomain();
        service.setName("Serviço Existente");

        when(serviceGateway.existsById(id)).thenReturn(true);
        when(serviceGateway.existsByIdAndActiveFalse(id)).thenReturn(false);
        when(serviceGateway.existsByIdNotAndName(id, service.getName())).thenReturn(true);

        // Act & Assert
        assertThrows(EntityConflictException.class, () -> {
            updateServiceByIdUseCase.execute(service, id, "imagem.jpg".getBytes(), "imagem.jpg");
        });

        verify(serviceGateway, times(1)).existsById(id);
        verify(serviceGateway, times(1)).existsByIdAndActiveFalse(id);
        verify(serviceGateway, times(1)).existsByIdNotAndName(id, service.getName());
        verify(serviceGateway, never()).save(any(ServiceDomain.class));
    }
}

