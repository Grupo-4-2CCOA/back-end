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
import sptech.school.projetoPI.core.application.usecases.user.employee.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = sptech.school.projetoPI.infrastructure.controllers.EmployeeController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class})
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateEmployeeUseCase createEmployeeUseCase;

    @MockBean
    private GetAllEmployeeUseCase getAllEmployeeUseCase;

    @MockBean
    private GetEmployeeByIdUseCase getEmployeeByIdUseCase;

    @MockBean
    private UpdateEmployeeByIdUseCase updateEmployeeByIdUseCase;

    @MockBean
    private DeleteEmployeeByIdUseCase deleteEmployeeByIdUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private final String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";

    private UserDomain criarUserDomain() {
        RoleDomain role = new RoleDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "FUNC", "Funcionário");
        return new UserDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Funcionário Teste", "12345678900", "funcionario@teste.com", "11999999999", "12345678", role);
    }

    @Test
    @DisplayName("Deve criar funcionário com sucesso retornando 201")
    void deveCriarFuncionarioComSucesso() throws Exception {
        // Arrange
        UserDomain userDomain = criarUserDomain();
        String employeeJson = """
                {
                    "name": "Funcionário Teste",
                    "email": "funcionario@teste.com",
                    "cpf": "12345678909",
                    "phone": "11999999999",
                    "cep": "12345678",
                    "role": 1
                }
                """;

        when(createEmployeeUseCase.execute(any())).thenReturn(userDomain);

        // Act & Assert
        mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar lista de funcionários com sucesso retornando 200")
    void deveRetornarListaDeFuncionarios() throws Exception {
        // Arrange
        UserDomain userDomain = criarUserDomain();
        when(getAllEmployeeUseCase.execute()).thenReturn(Collections.singletonList(userDomain));

        // Act & Assert
        mockMvc.perform(get("/funcionarios")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar funcionário por ID com sucesso retornando 200")
    void deveRetornarFuncionarioPorId() throws Exception {
        // Arrange
        UserDomain userDomain = criarUserDomain();
        when(getEmployeeByIdUseCase.execute(1)).thenReturn(userDomain);

        // Act & Assert
        mockMvc.perform(get("/funcionarios/1")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve atualizar funcionário com sucesso retornando 200")
    void deveAtualizarFuncionarioComSucesso() throws Exception {
        // Arrange
        UserDomain userDomain = criarUserDomain();
        String employeeJson = """
                {
                    "name": "Funcionário Atualizado",
                    "email": "atualizado@teste.com",
                    "cpf": "12345678909",
                    "phone": "11999999999",
                    "cep": "12345678",
                    "role": 1
                }
                """;

        when(updateEmployeeByIdUseCase.execute(any(), anyInt())).thenReturn(userDomain);

        // Act & Assert
        mockMvc.perform(put("/funcionarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(employeeJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar funcionário com sucesso retornando 204")
    void deveDeletarFuncionarioComSucesso() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/funcionarios/1")
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNoContent());
    }
}
