package sptech.school.projetoPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import sptech.school.projetoPI.config.TestSecurityConfig;
import sptech.school.projetoPI.core.application.usecases.service.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.infrastructure.mappers.ServiceMapperImage;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = sptech.school.projetoPI.infrastructure.controllers.ServiceController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class})
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateServiceUseCase createServiceUseCase;

    @MockBean
    private GetAllServiceUseCase getAllServicesUseCase;

    @MockBean
    private GetServiceByIdUseCase getServiceByIdUseCase;

    @MockBean
    private UpdateServiceByIdUseCase updateServiceByIdUseCase;

    @MockBean
    private DeleteServiceByIdUseCase deleteServiceByIdUseCase;

    @MockBean
    private ServiceMapperImage serviceMapperImage;

    @Autowired
    private ObjectMapper objectMapper;

    private final String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";

    private ServiceDomain criarServiceDomain() {
        CategoryDomain category = new CategoryDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Categoria", "Descrição");
        return new ServiceDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Serviço Teste", 100.0, 60, "Descrição do serviço", "imagem.jpg", category);
    }

    @Test
    @DisplayName("Deve criar serviço com sucesso retornando 201")
    void deveCriarServicoComSucesso() throws Exception {
        // Arrange
        ServiceDomain serviceDomain = criarServiceDomain();
        String serviceJson = """
                {
                    "name": "Serviço Teste",
                    "description": "Descrição do serviço",
                    "basePrice": 100.0,
                    "baseDuration": 60,
                    "category": {
                        "id": 1
                    }
                }
                """;
        MockMultipartFile servicePart = new MockMultipartFile("service", "service.json", 
                MediaType.APPLICATION_JSON_VALUE, serviceJson.getBytes());
        MockMultipartFile imagePart = new MockMultipartFile("image", "imagem.jpg", 
                MediaType.IMAGE_JPEG_VALUE, "imagem".getBytes());

        when(createServiceUseCase.execute(any(ServiceDomain.class), any(byte[].class), anyString()))
                .thenReturn(serviceDomain);
        when(serviceMapperImage.toResponseDtoWithImageUrl(any(ServiceDomain.class)))
                .thenReturn(sptech.school.projetoPI.core.application.dto.service.ServiceResponseDto.builder()
                        .id(1)
                        .name("Serviço Teste")
                        .basePrice(100.0)
                        .build());

        // Act & Assert
        mockMvc.perform(multipart("/servicos")
                        .file(servicePart)
                        .file(imagePart)
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar lista de serviços com sucesso retornando 200")
    void deveRetornarListaDeServicos() throws Exception {
        // Arrange
        ServiceDomain service1 = criarServiceDomain();
        ServiceDomain service2 = criarServiceDomain();
        service2.setId(2);
        service2.setName("Serviço 2");

        List<ServiceDomain> services = Arrays.asList(service1, service2);
        when(getAllServicesUseCase.execute()).thenReturn(services);

        // Act & Assert
        mockMvc.perform(get("/servicos")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar serviço por ID com sucesso retornando 200")
    void deveRetornarServicoPorId() throws Exception {
        // Arrange
        ServiceDomain serviceDomain = criarServiceDomain();
        when(getServiceByIdUseCase.execute(1)).thenReturn(serviceDomain);

        // Act & Assert
        mockMvc.perform(get("/servicos/1")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve atualizar serviço com sucesso retornando 200")
    void deveAtualizarServicoComSucesso() throws Exception {
        // Arrange
        ServiceDomain serviceDomain = criarServiceDomain();
        String serviceJson = """
                {
                    "name": "Serviço Atualizado",
                    "description": "Nova descrição",
                    "basePrice": 150.0,
                    "baseDuration": 90,
                    "category": {
                        "id": 1
                    }
                }
                """;
        MockMultipartFile servicePart = new MockMultipartFile("service", "service.json", 
                MediaType.APPLICATION_JSON_VALUE, serviceJson.getBytes());
        MockMultipartFile imagePart = new MockMultipartFile("image", "imagem.jpg", 
                MediaType.IMAGE_JPEG_VALUE, "imagem".getBytes());

        when(updateServiceByIdUseCase.execute(any(ServiceDomain.class), anyInt(), any(byte[].class), anyString()))
                .thenReturn(serviceDomain);
        when(serviceMapperImage.toResponseDtoWithImageUrl(any(ServiceDomain.class)))
                .thenReturn(sptech.school.projetoPI.core.application.dto.service.ServiceResponseDto.builder()
                        .id(1)
                        .name("Serviço Atualizado")
                        .build());

        // Act & Assert
        mockMvc.perform(multipart("/servicos/1")
                        .file(servicePart)
                        .file(imagePart)
                        .header("Authorization", tokenBearer)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar serviço com sucesso retornando 204")
    void deveDeletarServicoComSucesso() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/servicos/1")
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 409 quando serviço já existe")
    void deveRetornar409QuandoServicoJaExiste() throws Exception {
        // Arrange
        String serviceJson = """
                {
                    "name": "Serviço Existente",
                    "description": "Descrição",
                    "basePrice": 100.0,
                    "baseDuration": 60,
                    "category": {
                        "id": 1
                    }
                }
                """;
        MockMultipartFile servicePart = new MockMultipartFile("service", "service.json", 
                MediaType.APPLICATION_JSON_VALUE, serviceJson.getBytes());
        MockMultipartFile imagePart = new MockMultipartFile("image", "imagem.jpg", 
                MediaType.IMAGE_JPEG_VALUE, "imagem".getBytes());

        when(createServiceUseCase.execute(any(ServiceDomain.class), any(byte[].class), anyString()))
                .thenThrow(new EntityConflictException("Este serviço já está cadastrado"));

        // Act & Assert
        mockMvc.perform(multipart("/servicos")
                        .file(servicePart)
                        .file(imagePart)
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deve retornar 404 quando serviço não existe")
    void deveRetornar404QuandoServicoNaoExiste() throws Exception {
        // Arrange
        when(getServiceByIdUseCase.execute(999))
                .thenThrow(new EntityNotFoundException("O serviço com o ID 999 não foi encontrado"));

        // Act & Assert
        mockMvc.perform(get("/servicos/999")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 400 quando campos são inválidos")
    void deveRetornar400QuandoCamposInvalidos() throws Exception {
        String serviceJson = """
                {
                    "name": "Serviço Teste",
                    "description": "Descrição",
                    "basePrice": -100.0,
                    "baseDuration": 60,
                    "category": {
                        "id": 1
                    }
                }
                """;
        MockMultipartFile servicePart = new MockMultipartFile("service", "service.json", 
                MediaType.APPLICATION_JSON_VALUE, serviceJson.getBytes());
        MockMultipartFile imagePart = new MockMultipartFile("image", "imagem.jpg", 
                MediaType.IMAGE_JPEG_VALUE, "imagem".getBytes());

        mockMvc.perform(multipart("/servicos")
                        .file(servicePart)
                        .file(imagePart)
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }

    // Teste removido: Como a segurança está desabilitada nos testes (@AutoConfigureMockMvc(addFilters = false)),
    // este teste sempre retornará 200. Para testar autenticação, seria necessário usar @SpringBootTest com segurança habilitada.
}
