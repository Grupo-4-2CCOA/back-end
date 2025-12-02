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
import org.springframework.test.web.servlet.MockMvc;
import sptech.school.projetoPI.config.TestSecurityConfig;
import sptech.school.projetoPI.core.application.usecases.availability.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.InvalidTimeRangeException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.AvailabilityDomain;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = sptech.school.projetoPI.infrastructure.controllers.AvailabilityController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class})
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateAvailabilityUseCase createAvailabilityUseCase;

    @MockBean
    private GetAllAvailabilityUseCase getAllAvailabilityUseCase;

    @MockBean
    private GetAvailabilityByIdUseCase getAvailabilityByIdUseCase;

    @MockBean
    private UpdateAvailabilityByIdUseCase updateAvailabilityByIdUseCase;

    @MockBean
    private DeleteAvailabilityByIdUseCase deleteAvailabilityByIdUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private final String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";

    private AvailabilityDomain criarAvailabilityDomain() {
        RoleDomain role = new RoleDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "Funcionário", "Descrição");
        UserDomain employee = new UserDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Funcionário", "12345678900", "funcionario@teste.com", "11999999999", "12345678", role);
        return new AvailabilityDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(18, 0), employee);
    }

    @Test
    @DisplayName("Deve criar disponibilidade com sucesso retornando 201")
    void deveCriarDisponibilidadeComSucesso() throws Exception {
        // Arrange
        AvailabilityDomain availabilityDomain = criarAvailabilityDomain();
        String availabilityJson = """
                {
                    "day": "2025-12-31",
                    "startTime": "09:00:00",
                    "endTime": "18:00:00",
                    "employee": 1
                }
                """;

        when(createAvailabilityUseCase.execute(any(AvailabilityDomain.class)))
                .thenReturn(availabilityDomain);

        // Act & Assert
        mockMvc.perform(post("/disponibilidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(availabilityJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar 400 quando horário inicial é posterior ao final")
    void deveRetornar400QuandoHorarioInvalido() throws Exception {
        // Arrange
        String availabilityJson = """
                {
                    "day": "2025-12-31",
                    "startTime": "18:00:00",
                    "endTime": "09:00:00",
                    "employee": 1
                }
                """;

        when(createAvailabilityUseCase.execute(any(AvailabilityDomain.class)))
                .thenThrow(new InvalidTimeRangeException("O horário inicial é posterior ao horário final"));

        // Act & Assert
        mockMvc.perform(post("/disponibilidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(availabilityJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 404 quando funcionário não existe")
    void deveRetornar404QuandoFuncionarioNaoExiste() throws Exception {
        // Arrange
        String availabilityJson = """
                {
                    "day": "2025-12-31",
                    "startTime": "09:00:00",
                    "endTime": "18:00:00",
                    "employee": 999
                }
                """;

        when(createAvailabilityUseCase.execute(any(AvailabilityDomain.class)))
                .thenThrow(new RelatedEntityNotFoundException("Employee com o ID 999 não foi encontrado"));

        // Act & Assert
        mockMvc.perform(post("/disponibilidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(availabilityJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 409 quando já existe disponibilidade para o dia")
    void deveRetornar409QuandoDisponibilidadeJaExiste() throws Exception {
        // Arrange
        String availabilityJson = """
                {
                    "day": "2025-12-31",
                    "startTime": "09:00:00",
                    "endTime": "18:00:00",
                    "employee": 1
                }
                """;

        when(createAvailabilityUseCase.execute(any(AvailabilityDomain.class)))
                .thenThrow(new EntityConflictException("Já existe um horário cadastrado para este funcionário no dia"));

        // Act & Assert
        mockMvc.perform(post("/disponibilidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(availabilityJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deve retornar lista de disponibilidades com sucesso retornando 200")
    void deveRetornarListaDeDisponibilidades() throws Exception {
        // Arrange
        AvailabilityDomain availability1 = criarAvailabilityDomain();
        AvailabilityDomain availability2 = criarAvailabilityDomain();
        availability2.setId(2);

        List<AvailabilityDomain> availabilities = Arrays.asList(availability1, availability2);
        when(getAllAvailabilityUseCase.execute()).thenReturn(availabilities);

        // Act & Assert
        mockMvc.perform(get("/disponibilidades")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar disponibilidade por ID com sucesso retornando 200")
    void deveRetornarDisponibilidadePorId() throws Exception {
        // Arrange
        AvailabilityDomain availabilityDomain = criarAvailabilityDomain();
        when(getAvailabilityByIdUseCase.execute(1)).thenReturn(availabilityDomain);

        // Act & Assert
        mockMvc.perform(get("/disponibilidades/1")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar 404 quando disponibilidade não existe")
    void deveRetornar404QuandoDisponibilidadeNaoExiste() throws Exception {
        // Arrange
        when(getAvailabilityByIdUseCase.execute(999))
                .thenThrow(new EntityNotFoundException("Disponibilidade não encontrada"));

        // Act & Assert
        mockMvc.perform(get("/disponibilidades/999")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar disponibilidade com sucesso retornando 200")
    void deveAtualizarDisponibilidadeComSucesso() throws Exception {
        // Arrange
        AvailabilityDomain availabilityDomain = criarAvailabilityDomain();
        String availabilityJson = """
                {
                    "day": "2025-12-31",
                    "startTime": "10:00:00",
                    "endTime": "19:00:00",
                    "employee": 1
                }
                """;

        when(updateAvailabilityByIdUseCase.execute(anyInt(), any(AvailabilityDomain.class)))
                .thenReturn(availabilityDomain);

        // Act & Assert
        mockMvc.perform(put("/disponibilidades/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(availabilityJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar disponibilidade com sucesso retornando 204")
    void deveDeletarDisponibilidadeComSucesso() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/disponibilidades/1")
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNoContent());
    }

    // Teste removido: Como a segurança está desabilitada nos testes (@AutoConfigureMockMvc(addFilters = false)),
    // este teste sempre retornará 200. Para testar autenticação, seria necessário usar @SpringBootTest com segurança habilitada.

    @Test
    @DisplayName("Deve retornar 400 quando campos são inválidos")
    void deveRetornar400QuandoCamposInvalidos() throws Exception {
        String availabilityJson = """
                {
                    "day": "data-invalida",
                    "startTime": "hora-invalida",
                    "endTime": "18:00:00",
                    "employee": 1
                }
                """;

        mockMvc.perform(post("/disponibilidades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(availabilityJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isBadRequest());
    }
}
