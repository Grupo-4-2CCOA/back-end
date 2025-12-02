package sptech.school.projetoPI.usecases.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.service.CreateServiceUseCase;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.FileUploadGateway;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateServiceUseCaseTest {

    @Mock
    private ServiceGateway serviceGateway;

    @Mock
    private FileUploadGateway fileUploadGateway;

    @InjectMocks
    private CreateServiceUseCase createServiceUseCase;

    @Test
    @DisplayName("Deve criar serviço com sucesso quando dados são válidos")
    void deveCriarServicoComSucesso() {
        // Arrange
        CategoryDomain category = new CategoryDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Categoria", "Descrição");
        ServiceDomain service = new ServiceDomain();
        service.setName("Serviço Teste");
        service.setDescription("Descrição do serviço");
        service.setBasePrice(100.0);
        service.setBaseDuration(60);
        service.setCategory(category);
        service.setActive(true);

        ServiceDomain savedService = new ServiceDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Serviço Teste", 100.0, 60, "Descrição do serviço", "imagem.jpg", category);

        when(serviceGateway.existsByName(anyString())).thenReturn(false);
        when(fileUploadGateway.uploadFile(anyString(), any(byte[].class))).thenReturn("url-da-imagem");
        when(serviceGateway.save(any(ServiceDomain.class))).thenReturn(savedService);

        // Act
        ServiceDomain result = createServiceUseCase.execute(service, "imagem.jpg".getBytes(), "imagem.jpg");

        // Assert
        assertNotNull(result);
        assertEquals(savedService.getId(), result.getId());
        assertEquals(savedService.getName(), result.getName());
        verify(serviceGateway, times(1)).existsByName(anyString());
        verify(fileUploadGateway, times(1)).uploadFile(anyString(), any(byte[].class));
        verify(serviceGateway, times(1)).save(any(ServiceDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityConflictException quando serviço com mesmo nome já existe")
    void deveLancarExcecaoQuandoServicoJaExiste() {
        // Arrange
        CategoryDomain category = new CategoryDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Categoria", "Descrição");
        ServiceDomain service = new ServiceDomain();
        service.setName("Serviço Existente");
        service.setCategory(category);

        when(serviceGateway.existsByName(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(EntityConflictException.class, () -> {
            createServiceUseCase.execute(service, "imagem.jpg".getBytes(), "imagem.jpg");
        });

        verify(serviceGateway, times(1)).existsByName(anyString());
        verify(fileUploadGateway, never()).uploadFile(anyString(), any(byte[].class));
        verify(serviceGateway, never()).save(any(ServiceDomain.class));
    }
}

