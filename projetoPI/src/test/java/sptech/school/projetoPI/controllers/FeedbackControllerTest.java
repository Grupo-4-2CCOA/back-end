package sptech.school.projetoPI.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import sptech.school.projetoPI.core.application.usecases.feedback.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.FeedbackDomain;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.enums.Status;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = sptech.school.projetoPI.infrastructure.controllers.FeedbackController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class})
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateFeedbackUseCase createFeedbackUseCase;

    @MockBean
    private GetAllFeedbackUseCase getAllFeedbackUseCase;

    @MockBean
    private GetFeedbackByIdUseCase getFeedbackByIdUseCase;

    @MockBean
    private UpdateFeedbackByIdUseCase updateFeedbackByIdUseCase;

    @MockBean
    private DeleteFeedbackByIdUseCase deleteFeedbackByIdUseCase;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";

    private FeedbackDomain criarFeedbackDomain() {
        RoleDomain roleClient = new RoleDomain(2, true, LocalDateTime.now(), LocalDateTime.now(), "USER", "Cliente");
        RoleDomain roleEmployee = new RoleDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "FUNC", "Funcionário");
        
        UserDomain client = new UserDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Cliente", "12345678900", "cliente@teste.com", "11999999999", "12345678", roleClient);
        UserDomain employee = new UserDomain(2, true, LocalDateTime.now(), LocalDateTime.now(),
                "Funcionário", "98765432100", "funcionario@teste.com", "11888888888", "87654321", roleEmployee);
        
        ScheduleDomain scheduleDomain = new ScheduleDomain();
        scheduleDomain.setId(1);
        scheduleDomain.setCreatedAt(LocalDateTime.now());
        scheduleDomain.setUpdatedAt(LocalDateTime.now());
        scheduleDomain.setStatus(Status.COMPLETED);
        scheduleDomain.setAppointmentDatetime(LocalDateTime.now().plusDays(1));
        scheduleDomain.setClientDomain(client);
        scheduleDomain.setEmployeeDomain(employee);
        
        FeedbackDomain feedbackDomain = new FeedbackDomain();
        feedbackDomain.setId(1);
        feedbackDomain.setComment("Ótimo atendimento");
        feedbackDomain.setRating(5);
        feedbackDomain.setScheduleDomain(scheduleDomain);
        feedbackDomain.setCreatedAt(LocalDateTime.now());
        feedbackDomain.setUpdatedAt(LocalDateTime.now());
        return feedbackDomain;
    }

    @Test
    @DisplayName("Deve criar feedback com sucesso retornando 201")
    void deveCriarFeedbackComSucesso() throws Exception {
        // Arrange
        FeedbackDomain feedbackDomain = criarFeedbackDomain();
        String feedbackJson = """
                {
                    "scheduleId": 1,
                    "comment": "Ótimo atendimento",
                    "rating": 5
                }
                """;

        when(createFeedbackUseCase.execute(any())).thenReturn(feedbackDomain);
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString());

        // Act & Assert
        mockMvc.perform(post("/feedbacks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedbackJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar lista de feedbacks com sucesso retornando 200")
    void deveRetornarListaDeFeedbacks() throws Exception {
        // Arrange
        FeedbackDomain feedbackDomain = criarFeedbackDomain();
        Page<FeedbackDomain> feedbackPage = new PageImpl<>(Collections.singletonList(feedbackDomain), PageRequest.of(0, 22), 1);
        when(getAllFeedbackUseCase.execute(any())).thenReturn(feedbackPage);

        // Act & Assert
        mockMvc.perform(get("/feedbacks")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar feedback por ID com sucesso retornando 200")
    void deveRetornarFeedbackPorId() throws Exception {
        // Arrange
        FeedbackDomain feedbackDomain = criarFeedbackDomain();
        when(getFeedbackByIdUseCase.execute(1)).thenReturn(feedbackDomain);

        // Act & Assert
        mockMvc.perform(get("/feedbacks/1")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
