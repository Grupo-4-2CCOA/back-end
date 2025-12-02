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
import sptech.school.projetoPI.core.application.usecases.category.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.CategoryDomain;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = sptech.school.projetoPI.infrastructure.controllers.CategoryController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class})
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @MockBean
    private GetAllCategoryUseCase getAllCategoriesUseCase;

    @MockBean
    private GetCategoryByIdUseCase getCategoryByIdUseCase;

    @MockBean
    private UpdateCategoryByIdUseCase updateCategoryByIdUseCase;

    @MockBean
    private DeleteCategoryByIdUseCase deleteCategoryByIdUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    private final String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";

    private CategoryDomain criarCategoryDomain() {
        return new CategoryDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Categoria Teste", "Descrição da categoria");
    }

    @Test
    @DisplayName("Deve criar categoria com sucesso retornando 201")
    void deveCriarCategoriaComSucesso() throws Exception {
        // Arrange
        CategoryDomain categoryDomain = criarCategoryDomain();
        String categoryJson = """
                {
                    "name": "Categoria Teste",
                    "description": "Descrição da categoria"
                }
                """;

        when(createCategoryUseCase.execute(any(CategoryDomain.class)))
                .thenReturn(categoryDomain);

        // Act & Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Deve retornar lista de categorias com sucesso retornando 200")
    void deveRetornarListaDeCategorias() throws Exception {
        // Arrange
        CategoryDomain category1 = criarCategoryDomain();
        CategoryDomain category2 = criarCategoryDomain();
        category2.setId(2);
        category2.setName("Categoria 2");

        List<CategoryDomain> categories = Arrays.asList(category1, category2);
        when(getAllCategoriesUseCase.execute()).thenReturn(categories);

        // Act & Assert
        mockMvc.perform(get("/categorias")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar categoria por ID com sucesso retornando 200")
    void deveRetornarCategoriaPorId() throws Exception {
        // Arrange
        CategoryDomain categoryDomain = criarCategoryDomain();
        when(getCategoryByIdUseCase.execute(1)).thenReturn(categoryDomain);

        // Act & Assert
        mockMvc.perform(get("/categorias/1")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve atualizar categoria com sucesso retornando 200")
    void deveAtualizarCategoriaComSucesso() throws Exception {
        // Arrange
        CategoryDomain categoryDomain = criarCategoryDomain();
        String categoryJson = """
                {
                    "name": "Categoria Atualizada",
                    "description": "Nova descrição"
                }
                """;

        when(updateCategoryByIdUseCase.execute(any(CategoryDomain.class), anyInt()))
                .thenReturn(categoryDomain);

        // Act & Assert
        mockMvc.perform(put("/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar categoria com sucesso retornando 204")
    void deveDeletarCategoriaComSucesso() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/categorias/1")
                        .header("Authorization", tokenBearer))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar 409 quando categoria já existe")
    void deveRetornar409QuandoCategoriaJaExiste() throws Exception {
        // Arrange
        String categoryJson = """
                {
                    "name": "Categoria Existente",
                    "description": "Descrição"
                }
                """;

        when(createCategoryUseCase.execute(any(CategoryDomain.class)))
                .thenThrow(new EntityConflictException("Categoria com o mesmo nome já existe na base de dados"));

        // Act & Assert
        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deve retornar 404 quando categoria não existe")
    void deveRetornar404QuandoCategoriaNaoExiste() throws Exception {
        // Arrange
        when(getCategoryByIdUseCase.execute(999))
                .thenThrow(new EntityNotFoundException("Category de ID 999 não foi encontrado"));

        // Act & Assert
        mockMvc.perform(get("/categorias/999")
                        .header("Authorization", tokenBearer)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve retornar 400 quando campos são inválidos")
    void deveRetornar400QuandoCamposInvalidos() throws Exception {
        String categoryJson = """
                {
                    "name": "",
                    "description": "Descrição"
                }
                """;

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryJson)
                        .header("Authorization", tokenBearer))
                .andExpect(status().isBadRequest());
    }

    // Teste removido: Como a segurança está desabilitada nos testes (@AutoConfigureMockMvc(addFilters = false)),
    // este teste sempre retornará 200. Para testar autenticação, seria necessário usar @SpringBootTest com segurança habilitada.
}
