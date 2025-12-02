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
import sptech.school.projetoPI.core.application.usecases.role.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.RoleDomain;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = sptech.school.projetoPI.infrastructure.controllers.RoleController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class})
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateRoleUseCase createRoleUseCase;

    @MockBean
    private GetAllRoleUseCase getAllRoleUseCase;

    @MockBean
    private GetRoleByIdUseCase getRoleByIdUseCase;

    @MockBean
    private UpdateRoleByIdUseCase updateRoleByIdUseCase;

    @MockBean
    private DeleteRoleByIdUseCase deleteRoleByIdUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private final String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";

    private RoleDomain criarRoleDomain() {
        return new RoleDomain(1, true, LocalDateTime.now(), LocalDateTime.now(), "EMPLOYEE", "Descrição");
    }

    @Test
    @DisplayName("Deve criar role com sucesso retornando 201")
    void deveCriarRoleComSucesso() throws Exception {
        // Arrange
        RoleDomain roleDomain = criarRoleDomain();
        String roleJson = """
                {
                    "name": "EMPLOYEE",
                    "description": "Descrição"
                }
                """;

        when(createRoleUseCase.execute(any())).thenReturn(roleDomain);

        // Act & Assert
        mockMvc.perform(post("/cargos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roleJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar lista de roles com sucesso retornando 200")
    void deveRetornarListaDeRoles() throws Exception {
        // Arrange
        RoleDomain roleDomain = criarRoleDomain();
        when(getAllRoleUseCase.execute()).thenReturn(Collections.singletonList(roleDomain));

        // Act & Assert
        mockMvc.perform(get("/cargos")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar role por ID com sucesso retornando 200")
    void deveRetornarRolePorId() throws Exception {
        // Arrange
        RoleDomain roleDomain = criarRoleDomain();
        when(getRoleByIdUseCase.execute(1)).thenReturn(roleDomain);

        // Act & Assert
        mockMvc.perform(get("/cargos/1")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
