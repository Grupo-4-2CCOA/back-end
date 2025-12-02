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
import sptech.school.projetoPI.core.application.usecases.scheduleItem.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.core.enums.Status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = sptech.school.projetoPI.infrastructure.controllers.ScheduleItemController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class})
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class ScheduleItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateScheduleItemUseCase createScheduleItemUseCase;

    @MockBean
    private GetAllScheduleItemUseCase getAllScheduleItemsUseCase;

    @MockBean
    private GetScheduleItemByIdUseCase getScheduleItemByIdUseCase;

    @MockBean
    private UpdateScheduleItemByIdUseCase updateScheduleItemByIdUseCase;

    @MockBean
    private DeleteScheduleItemByIdUseCase deleteScheduleItemByIdUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private final String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";

    private ScheduleItemDomain criarScheduleItemDomain() {
        sptech.school.projetoPI.core.domains.ScheduleDomain scheduleDomain = new sptech.school.projetoPI.core.domains.ScheduleDomain();
        scheduleDomain.setId(1);
        scheduleDomain.setStatus(Status.COMPLETED);
        scheduleDomain.setAppointmentDatetime(LocalDateTime.now().plusDays(1));
        
        sptech.school.projetoPI.core.domains.CategoryDomain category = new sptech.school.projetoPI.core.domains.CategoryDomain(
                1, true, LocalDateTime.now(), LocalDateTime.now(), "Categoria", "Descrição");
        sptech.school.projetoPI.core.domains.ServiceDomain serviceDomain = new sptech.school.projetoPI.core.domains.ServiceDomain(
                1, true, LocalDateTime.now(), LocalDateTime.now(), "Serviço", 100.0, 60, "Descrição", "imagem.jpg", category);
        
        ScheduleItemDomain scheduleItem = new ScheduleItemDomain();
        scheduleItem.setId(1);
        scheduleItem.setFinalPrice(100.0);
        scheduleItem.setDiscount(10.0);
        scheduleItem.setSchedule(scheduleDomain);
        scheduleItem.setService(serviceDomain);
        scheduleItem.setCreatedAt(LocalDateTime.now());
        scheduleItem.setUpdatedAt(LocalDateTime.now());
        return scheduleItem;
    }

    @Test
    @DisplayName("Deve criar item de agendamento com sucesso retornando 201")
    void deveCriarScheduleItemComSucesso() throws Exception {
        // Arrange
        ScheduleItemDomain scheduleItemDomain = criarScheduleItemDomain();
        String scheduleItemJson = """
                {
                    "finalPrice": 100.0,
                    "discount": 10.0,
                    "service": 1
                }
                """;

        when(createScheduleItemUseCase.execute(any(ScheduleItemDomain.class)))
                .thenReturn(scheduleItemDomain);

        // Act & Assert
        mockMvc.perform(post("/itens-agendamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleItemJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar 404 quando agendamento não existe")
    void deveRetornar404QuandoAgendamentoNaoExiste() throws Exception {
        // Arrange
        String scheduleItemJson = """
                {
                    "finalPrice": 100.0,
                    "discount": 10.0,
                    "service": 1
                }
                """;

        // O ScheduleItemDomain precisa ter um ScheduleDomain com ID 999 para o UseCase lançar a exceção
        ScheduleItemDomain scheduleItemDomain = criarScheduleItemDomain();
        sptech.school.projetoPI.core.domains.ScheduleDomain scheduleDomain = new sptech.school.projetoPI.core.domains.ScheduleDomain();
        scheduleDomain.setId(999);
        scheduleItemDomain.setSchedule(scheduleDomain);

        when(createScheduleItemUseCase.execute(any(ScheduleItemDomain.class)))
                .thenThrow(new RelatedEntityNotFoundException("O agendamento com o ID 999 não foi encontrado"));

        // Act & Assert
        mockMvc.perform(post("/itens-agendamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleItemJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 404 quando serviço não existe")
    void deveRetornar404QuandoServicoNaoExiste() throws Exception {
        // Arrange
        String scheduleItemJson = """
                {
                    "finalPrice": 100.0,
                    "discount": 10.0,
                    "service": 999
                }
                """;

        when(createScheduleItemUseCase.execute(any(ScheduleItemDomain.class)))
                .thenThrow(new RelatedEntityNotFoundException("O serviço com o ID 999 não foi encontrado"));

        // Act & Assert
        mockMvc.perform(post("/itens-agendamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleItemJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar lista de itens de agendamento com sucesso retornando 200")
    void deveRetornarListaDeScheduleItems() throws Exception {
        // Arrange
        ScheduleItemDomain scheduleItem1 = criarScheduleItemDomain();
        ScheduleItemDomain scheduleItem2 = criarScheduleItemDomain();
        scheduleItem2.setId(2);

        List<ScheduleItemDomain> scheduleItems = Arrays.asList(scheduleItem1, scheduleItem2);
        when(getAllScheduleItemsUseCase.execute()).thenReturn(scheduleItems);

        // Act & Assert
        mockMvc.perform(get("/itens-agendamento")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar item de agendamento por ID com sucesso retornando 200")
    void deveRetornarScheduleItemPorId() throws Exception {
        // Arrange
        ScheduleItemDomain scheduleItemDomain = criarScheduleItemDomain();
        when(getScheduleItemByIdUseCase.execute(1)).thenReturn(scheduleItemDomain);

        // Act & Assert
        mockMvc.perform(get("/itens-agendamento/1")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar 404 quando item de agendamento não existe")
    void deveRetornar404QuandoScheduleItemNaoExiste() throws Exception {
        // Arrange
        when(getScheduleItemByIdUseCase.execute(999))
                .thenThrow(new EntityNotFoundException("Item de agendamento não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/itens-agendamento/999")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar item de agendamento com sucesso retornando 200")
    void deveAtualizarScheduleItemComSucesso() throws Exception {
        // Arrange
        ScheduleItemDomain scheduleItemDomain = criarScheduleItemDomain();
        String scheduleItemJson = """
                {
                    "finalPrice": 150.0,
                    "discount": 15.0,
                    "service": 1
                }
                """;

        when(updateScheduleItemByIdUseCase.execute(any(ScheduleItemDomain.class), anyInt()))
                .thenReturn(scheduleItemDomain);

        // Act & Assert
        mockMvc.perform(put("/itens-agendamento/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleItemJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar item de agendamento com sucesso retornando 204")
    void deveDeletarScheduleItemComSucesso() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/itens-agendamento/1")
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 400 quando campos são inválidos")
    void deveRetornar400QuandoCamposInvalidos() throws Exception {
        String scheduleItemJson = """
                {
                    "finalPrice": -100.0,
                    "discount": 10.0,
                    "service": 1
                }
                """;

        mockMvc.perform(post("/itens-agendamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleItemJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar 400 quando campo obrigatório está faltando")
    void deveRetornar400QuandoCampoObrigatorioFaltando() throws Exception {
        String scheduleItemJson = """
                {
                    "discount": 10.0,
                    "service": 1
                }
                """;

        mockMvc.perform(post("/itens-agendamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scheduleItemJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isBadRequest());
    }

    // Teste removido: Como a segurança está desabilitada nos testes (@AutoConfigureMockMvc(addFilters = false)),
    // este teste sempre retornará 200. Para testar autenticação, seria necessário usar @SpringBootTest com segurança habilitada.
}
