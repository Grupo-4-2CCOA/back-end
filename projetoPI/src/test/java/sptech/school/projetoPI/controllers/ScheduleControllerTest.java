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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sptech.school.projetoPI.config.TestSecurityConfig;
import sptech.school.projetoPI.core.application.usecases.schedule.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.ScheduleDomain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = sptech.school.projetoPI.infrastructure.controllers.ScheduleController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class})
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateScheduleUseCase createScheduleUseCase;

    @MockBean
    private GetAllScheduleUseCase getAllScheduleUseCase;

    @MockBean
    private GetScheduleByIdUseCase getScheduleByIdUseCase;

    @MockBean
    private UpdateScheduleByIdUseCase updateScheduleByIdUseCase;

    @MockBean
    private DeleteScheduleByIdUseCase deleteScheduleByIdUseCase;

    @MockBean
    private GetServiceNamesByScheduleIdUseCase getServiceNamesByScheduleIdUseCase;

    @MockBean
    private GetAllSchedulesByClient getAllSchedulesByClient;

    @Autowired
    private ObjectMapper objectMapper;

    private final String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";

    private ScheduleDomain criarScheduleDomain() {
        ScheduleDomain schedule = new ScheduleDomain();
        schedule.setId(1);
        schedule.setAppointmentDatetime(LocalDateTime.now().plusDays(1));
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());
        return schedule;
    }

    @Test
    @DisplayName("Deve criar agendamento com sucesso retornando 201")
    void deveCriarAgendamentoComSucesso() throws Exception {
        // Arrange
        ScheduleDomain scheduleDomain = criarScheduleDomain();
        String scheduleJson = """
                {
                    "client": 1,
                    "employee": 1,
                    "appointmentDatetime": "2025-12-31T10:00:00",
                    "status": "ACTIVE",
                    "paymentType": 1,
                    "items": [
                        {
                            "finalPrice": 100.0,
                            "discount": 10.0,
                            "service": 1
                        }
                    ]
                }
                """;

        when(createScheduleUseCase.execute(any(ScheduleDomain.class)))
                .thenReturn(scheduleDomain);

        // Act & Assert
        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar 404 quando cliente não existe")
    void deveRetornar404QuandoClienteNaoExiste() throws Exception {
        // Arrange
        String scheduleJson = """
                {
                    "client": 999,
                    "employee": 1,
                    "appointmentDatetime": "2025-12-31T10:00:00",
                    "status": "ACTIVE",
                    "items": [
                        {
                            "finalPrice": 100.0,
                            "discount": 10.0,
                            "service": 1
                        }
                    ]
                }
                """;

        when(createScheduleUseCase.execute(any(ScheduleDomain.class)))
                .thenThrow(new RelatedEntityNotFoundException("Cliente com ID 999 não encontrado"));

        // Act & Assert
        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 409 quando horário já está ocupado")
    void deveRetornar409QuandoHorarioJaOcupado() throws Exception {
        // Arrange
        String scheduleJson = """
                {
                    "client": 1,
                    "employee": 1,
                    "appointmentDatetime": "2025-12-31T10:00:00",
                    "status": "ACTIVE",
                    "items": [
                        {
                            "finalPrice": 100.0,
                            "discount": 10.0,
                            "service": 1
                        }
                    ]
                }
                """;

        when(createScheduleUseCase.execute(any(ScheduleDomain.class)))
                .thenThrow(new EntityConflictException("Horário já ocupado"));

        // Act & Assert
        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deve retornar 500 quando horário está no passado")
    void deveRetornar500QuandoHorarioNoPassado() throws Exception {
        // Arrange
        String scheduleJson = """
                {
                    "client": 1,
                    "employee": 1,
                    "appointmentDatetime": "2020-01-01T10:00:00",
                    "status": "ACTIVE",
                    "items": [
                        {
                            "finalPrice": 100.0,
                            "discount": 10.0,
                            "service": 1
                        }
                    ]
                }
                """;

        when(createScheduleUseCase.execute(any(ScheduleDomain.class)))
                .thenThrow(new Exception("O Horário deve estar no futuro"));

        // Act & Assert
        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar lista de agendamentos com sucesso retornando 200")
    void deveRetornarListaDeAgendamentos() throws Exception {
        // Arrange
        ScheduleDomain schedule1 = criarScheduleDomain();
        ScheduleDomain schedule2 = criarScheduleDomain();
        schedule2.setId(2);

        Page<ScheduleDomain> schedulePage = new PageImpl<>(Arrays.asList(schedule1, schedule2), PageRequest.of(0, 5), 2);
        when(getAllScheduleUseCase.execute(any(org.springframework.data.domain.Pageable.class), any(), any(), any())).thenReturn(schedulePage);

        // Act & Assert
        mockMvc.perform(get("/agendamentos")
                        .param("page", "0")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar agendamento por ID com sucesso retornando 200")
    void deveRetornarAgendamentoPorId() throws Exception {
        // Arrange
        ScheduleDomain scheduleDomain = criarScheduleDomain();
        when(getScheduleByIdUseCase.execute(1)).thenReturn(scheduleDomain);

        // Act & Assert
        mockMvc.perform(get("/agendamentos/1")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar 404 quando agendamento não existe")
    void deveRetornar404QuandoAgendamentoNaoExiste() throws Exception {
        // Arrange
        when(getScheduleByIdUseCase.execute(999))
                .thenThrow(new EntityNotFoundException("Agendamento não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/agendamentos/999")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar agendamento com sucesso retornando 200")
    void deveAtualizarAgendamentoComSucesso() throws Exception {
        // Arrange
        ScheduleDomain scheduleDomain = criarScheduleDomain();
        String scheduleJson = """
                {
                    "client": 1,
                    "employee": 1,
                    "appointmentDatetime": "2025-12-31T11:00:00",
                    "status": "ACTIVE",
                    "items": [
                        {
                            "finalPrice": 100.0,
                            "discount": 10.0,
                            "service": 1
                        }
                    ]
                }
                """;

        when(updateScheduleByIdUseCase.execute(any(ScheduleDomain.class), anyInt()))
                .thenReturn(scheduleDomain);

        // Act & Assert
        mockMvc.perform(put("/agendamentos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar agendamento com sucesso retornando 204")
    void deveDeletarAgendamentoComSucesso() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/agendamentos/1")
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 400 quando campos são inválidos")
    void deveRetornar400QuandoCamposInvalidos() throws Exception {
        String scheduleJson = """
                {
                    "client": -1,
                    "employee": 1,
                    "appointmentDatetime": "data-invalida",
                    "status": "ACTIVE",
                    "items": []
                }
                """;

        mockMvc.perform(post("/agendamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isBadRequest());
    }

    // Teste removido: Como a segurança está desabilitada nos testes (@AutoConfigureMockMvc(addFilters = false)),
    // este teste sempre retornará 200. Para testar autenticação, seria necessário usar @SpringBootTest com segurança habilitada.
}
