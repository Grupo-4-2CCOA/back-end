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
import sptech.school.projetoPI.core.application.usecases.user.client.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = sptech.school.projetoPI.infrastructure.controllers.ClientController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class})
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateClientUseCase createClientUseCase;

    @MockBean
    private GetAllClientsUseCase getAllClientsUseCase;

    @MockBean
    private GetClientByIdUseCase getClientByIdUseCase;

    @MockBean
    private UpdateClientByIdUseCase updateClientByIdUseCase;

    @MockBean
    private DeleteClientByIdUseCase deleteClientByIdUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private final String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";

    private UserDomain criarUserDomain() {
        RoleDomain role = new RoleDomain(2, true, LocalDateTime.now(), LocalDateTime.now(), "USER", "Cliente");
        return new UserDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Cliente Teste", "12345678900", "cliente@teste.com", "11999999999", "12345678", role);
    }

    @Test
    @DisplayName("Deve criar cliente com sucesso retornando 201")
    void deveCriarClienteComSucesso() throws Exception {
        // Arrange
        UserDomain userDomain = criarUserDomain();
        String clientJson = """
                {
                    "name": "Cliente Teste",
                    "email": "cliente@teste.com",
                    "cpf": "12345678909",
                    "phone": "11999999999",
                    "cep": "12345678",
                    "role": 2
                }
                """;

        when(createClientUseCase.execute(any())).thenReturn(userDomain);

        // Act & Assert
        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar lista de clientes com sucesso retornando 200")
    void deveRetornarListaDeClientes() throws Exception {
        // Arrange
        UserDomain userDomain = criarUserDomain();
        when(getAllClientsUseCase.execute()).thenReturn(Collections.singletonList(userDomain));

        // Act & Assert
        mockMvc.perform(get("/clientes")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar cliente por ID com sucesso retornando 200")
    void deveRetornarClientePorId() throws Exception {
        // Arrange
        UserDomain userDomain = criarUserDomain();
        when(getClientByIdUseCase.execute(1)).thenReturn(userDomain);

        // Act & Assert
        mockMvc.perform(get("/clientes/1")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve atualizar cliente com sucesso retornando 200")
    void deveAtualizarClienteComSucesso() throws Exception {
        // Arrange
        UserDomain userDomain = criarUserDomain();
        String clientJson = """
                {
                    "name": "Cliente Atualizado",
                    "email": "atualizado@teste.com",
                    "cpf": "12345678909",
                    "phone": "11999999999",
                    "cep": "12345678",
                    "role": 2
                }
                """;

        when(updateClientByIdUseCase.execute(any(), anyInt())).thenReturn(userDomain);

        // Act & Assert
        mockMvc.perform(put("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso retornando 204")
    void deveDeletarClienteComSucesso() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/clientes/1")
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNoContent());
    }
}
