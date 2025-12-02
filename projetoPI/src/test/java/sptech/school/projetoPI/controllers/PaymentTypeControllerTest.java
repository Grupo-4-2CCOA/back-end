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
import sptech.school.projetoPI.core.application.usecases.paymentType.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = sptech.school.projetoPI.infrastructure.controllers.PaymentTypeController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class})
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class PaymentTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreatePaymentTypeUseCase createPaymentTypeUseCase;

    @MockBean
    private GetAllPaymentTypeUseCase getAllPaymentTypeUseCase;

    @MockBean
    private GetPaymentTypeByIdUseCase getPaymentTypeByIdUseCase;

    @MockBean
    private UpdatePaymentTypeByIdUseCase updatePaymentTypeByIdUseCase;

    @MockBean
    private DeletePaymentTypeByIdUseCase deletePaymentTypeByIdUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private final String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";

    private sptech.school.projetoPI.core.domains.PaymentTypeDomain criarPaymentTypeDomain() {
        return new sptech.school.projetoPI.core.domains.PaymentTypeDomain(
                1, true, java.time.LocalDateTime.now(), java.time.LocalDateTime.now(),
                "Cartão", "Cartão de crédito"
        );
    }

    @Test
    @DisplayName("Deve criar tipo de pagamento com sucesso retornando 201")
    void deveCriarPaymentTypeComSucesso() throws Exception {
        // Arrange
        sptech.school.projetoPI.core.domains.PaymentTypeDomain paymentTypeDomain = criarPaymentTypeDomain();
        String paymentTypeJson = """
                {
                    "name": "Cartão",
                    "description": "Cartão de crédito"
                }
                """;

        when(createPaymentTypeUseCase.execute(any())).thenReturn(paymentTypeDomain);

        // Act & Assert
        mockMvc.perform(post("/pagamentos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(paymentTypeJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar lista de tipos de pagamento com sucesso retornando 200")
    void deveRetornarListaDePaymentTypes() throws Exception {
        // Arrange
        sptech.school.projetoPI.core.domains.PaymentTypeDomain paymentTypeDomain = criarPaymentTypeDomain();
        when(getAllPaymentTypeUseCase.execute()).thenReturn(java.util.Collections.singletonList(paymentTypeDomain));

        // Act & Assert
        mockMvc.perform(get("/pagamentos")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar tipo de pagamento por ID com sucesso retornando 200")
    void deveRetornarPaymentTypePorId() throws Exception {
        // Arrange
        sptech.school.projetoPI.core.domains.PaymentTypeDomain paymentTypeDomain = criarPaymentTypeDomain();
        when(getPaymentTypeByIdUseCase.execute(1)).thenReturn(paymentTypeDomain);

        // Act & Assert
        mockMvc.perform(get("/pagamentos/1")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
