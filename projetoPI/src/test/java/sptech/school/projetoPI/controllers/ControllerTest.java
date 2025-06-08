package sptech.school.projetoPI.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.startsWith;

@AutoConfigureMockMvc
public abstract class ControllerTest<Entity, Service> {

    @Autowired
    MockMvc mockMvc;

    String tokenBearer = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmYXBydWZlckBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6IiIsImlhdCI6MTc0ODM3OTQ5NiwiZXhwIjoxNzUxOTc5NDk2fQ.WoY7fhyt5nsLyrZHWCc7MvzmKbGdj1eyyHy3CGO3EQxNc3NXsI1X6AtxKVlsIIh5uOh_bhegnWVOLJHeXGwfXQ";
    String url = "http://localhost:8080";

    protected abstract Entity getEntity();
    protected abstract Service getService();

    protected abstract void whenSignThenReturn();
    protected abstract void whenGetAllThenReturn(boolean hasContent);
    protected abstract void whenGetByIdThenReturn();
    protected abstract void whenUpdateByIdThenReturn();

    protected abstract String getUrl();
    protected String getUrlWithId() {
        return getUrl() + "/{id}";
    }

    protected abstract String getValidJsonRequestBody();
    protected abstract String getJsonWithInvalidFieldContent();
    protected abstract String getJsonWithMissingField();
    protected abstract String getJsonWithNullValue();
    protected abstract String getJsonWithUnprocessableRequestBody();

    protected boolean needsJwtToken() {
        return true;
    }

    @Test
    @DisplayName("Quando método POST for chamado com credenciais válidas, deve retornar 201")
    void executePostMethodWithValidFieldsMustReturn201Test() throws Exception {
        whenSignThenReturn();

        mockMvc.perform(post(getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getValidJsonRequestBody())
                )
                .andExpect(status().is(201));
    }

    @Test
    @DisplayName("Quando método POST for chamado com campos inválidos, deve retornar 400")
    void executePostMethodWithInvalidFieldContentMustReturn400Test() throws Exception {
        mockMvc.perform(post(getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getJsonWithInvalidFieldContent())
                )
                .andExpect(status().is(400))
                .andExpect(content().string(startsWith("Campos inválidos:")));
    }

    @Test
    @DisplayName("Quando método POST for chamado faltando campos, deve retornar 400")
    void executePostMethodWithMissingFieldMustReturn400Test() throws Exception {
        mockMvc.perform(post(getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getJsonWithMissingField())
                )
                .andExpect(status().is(400))
                .andExpect(content().string(startsWith("Campos inválidos:")));
    }

    @Test
    @DisplayName("Quando método POST for chamado com valores nulos em campos Non-Null, deve retornar 400")
    void executePostMethodWithNullValueInANonNullFieldMustReturn400Test() throws Exception {
        mockMvc.perform(post(getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getJsonWithNullValue())
                )
                .andExpect(status().is(400))
                .andExpect(content().string(startsWith("Campos inválidos:")));
    }

    @Test
    @DisplayName("Quando método POST for chamado com corpo de requisição não processável, deve retornar 400")
    void executePostMethodWithUnprocessableRequestBodyMustReturn400Test() throws Exception {
        mockMvc.perform(post(getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getJsonWithUnprocessableRequestBody())
                )
                .andExpect(status().is(400))
                .andExpect(content().string(startsWith("Falha ao processar corpo de requisição:")));
    }

    @Test
    @DisplayName("Quando método POST for chamado sem TokenBearer, deve retornar 401")
    void executePostMethodWithInvalidTokenBearerMustReturn401Test() throws Exception {
        assumeTrue(needsJwtToken());
        mockMvc.perform(post(getUrl())).andExpect(status().is(401));
    }

    @Test
    @DisplayName("Quando método GET for chamado com entidades registradas, deve retornar 200")
    void executeGetMethodWithRecordedEntitiesMustReturn200Test() throws Exception {
        whenGetAllThenReturn(true);

        mockMvc.perform(get(getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                )
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Quando método GET for chamado sem entidades registradas, deve retornar 204")
    void executeGetMethodWithoutRecordedEntitiesMustReturn204Test() throws Exception {
        whenGetAllThenReturn(false);

        mockMvc.perform(get(getUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                )
                .andExpect(status().is(204));
    }

    @Test
    @DisplayName("Quando método GET ALL for chamado sem TokenBearer, deve retornar 401")
    void executeGetAllMethodWithoutTokenBearerMustReturn401Test() throws Exception {
        mockMvc.perform(get(getUrl())).andExpect(status().is(401));
    }

    @Test
    @DisplayName("Quando método GET for chamado com ID válido, deve retornar 200")
    void executeGetMethodWithValidPathVariableTypeMustReturn200Test() throws Exception {
        whenGetByIdThenReturn();

        mockMvc.perform(get(getUrlWithId(), 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                )
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Quando método GET for chamado com ID inválido, deve retornar 400")
    void executeGetMethodWithInvalidPathVariableTypeMustReturn400Test() throws Exception {
        mockMvc.perform(get(getUrlWithId(), "ABC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                )
                .andExpect(status().is(400))
                .andExpect(content().string(startsWith("Tipo de parâmetro da requisição inválida:")));
    }

    @Test
    @DisplayName("Quando método GET for chamado sem TokenBearer, deve retornar 401")
    void executeGetMethodWithoutTokenBearerMustReturn401Test() throws Exception {
        mockMvc.perform(get(getUrlWithId(), 1)).andExpect(status().is(401));
    }

    @Test
    @DisplayName("Quando método PUT for chamado com credenciais válidas, deve retornar 200")
    void executePutMethodWithValidFieldsMustReturn200Test() throws Exception {
        whenUpdateByIdThenReturn();

        mockMvc.perform(put(getUrlWithId(), 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getValidJsonRequestBody())
                )
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("Quando método PUT for chamado com campos inválidos, deve retornar 400")
    void executePutMethodWithInvalidFieldContentMustReturn400Test() throws Exception {
        mockMvc.perform(put(getUrlWithId(), 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getJsonWithInvalidFieldContent())
                )
                .andExpect(status().is(400))
                .andExpect(content().string(startsWith("Campos inválidos:")));
    }

    @Test
    @DisplayName("Quando método PUT for chamado com campos faltando, deve retornar 400")
    void executePutMethodWithMissingFieldMustReturn400Test() throws Exception {
        mockMvc.perform(put(getUrlWithId(), 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getJsonWithMissingField())
                )
                .andExpect(status().is(400))
                .andExpect(content().string(startsWith("Campos inválidos:")));
    }

    @Test
    @DisplayName("Quando método PUT for chamado com valores nulos em campos Non-Null, deve retornar 400")
    void executePutMethodWithNullValueInANonNullFieldMustReturn400Test() throws Exception {
        mockMvc.perform(put(getUrlWithId(), 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getJsonWithNullValue())
                )
                .andExpect(status().is(400))
                .andExpect(content().string(startsWith("Campos inválidos:")));
    }

    @Test
    @DisplayName("Quando método PUT for chamado com corpo de requisição não processável, deve retornar 400")
    void executePutMethodWithUnprocessableRequestBodyMustReturn400Test() throws Exception {
        mockMvc.perform(put(getUrlWithId(), 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getJsonWithUnprocessableRequestBody())
                )
                .andExpect(status().is(400))
                .andExpect(content().string(startsWith("Falha ao processar corpo de requisição:")));
    }

    @Test
    @DisplayName("Quando método PUT for chamado com ID inválido, deve retornar 400")
    void executePutMethodWithInvalidPathVariableTypeMustReturn400Test() throws Exception {
        mockMvc.perform(put(getUrlWithId(), "ABC")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", tokenBearer)
                        .content(getValidJsonRequestBody())
                )
                .andExpect(status().is(400))
                .andExpect(content().string(startsWith("Tipo de parâmetro da requisição inválida:")));
    }

    @Test
    @DisplayName("Quando método PUT for chamado sem TokenBearer, deve retornar 401")
    void executePutMethodWithInvalidTokenBearerMustReturn401Test() throws Exception {
        mockMvc.perform(put(getUrlWithId(), 1)).andExpect(status().is(401));
    }

    @Test
    @DisplayName("Quando método DELETE for chamado com ID válido, deve retornar 204")
    void executeDeleteMethodWithValidPathVariableMustReturn204Test() throws Exception {
        mockMvc.perform(delete(getUrlWithId(), 1)
                        .header("Authorization", tokenBearer)
                )
                .andExpect(status().is(204));
    }

    @Test
    @DisplayName("Quando método DELETE for chamado com ID inválido, deve retornar 400")
    void executeDeleteMethodWithInvalidPathVariableTypeMustReturn400Test() throws Exception {
        mockMvc.perform(delete(getUrlWithId(), "ABC")
                        .header("Authorization", tokenBearer)
                )
                .andExpect(status().is(400));
    }

    @Test
    @DisplayName("Quando método DELETE for chamado sem TokenBearer, deve retornar 401")
    void executeDeleteMethodWithInvalidTokenBearerMustReturn401Test() throws Exception {
        mockMvc.perform(delete(getUrlWithId(), 1)).andExpect(status().is(401));
    }
}
