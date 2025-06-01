package sptech.school.projetoPI.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.services.user.ClientService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClientControllerTest extends ControllerTest<Client, ClientService> {

    @MockBean
    private ClientService service;

    @Override
    protected Client getEntity() {
        return new Client();
    }

    @Override
    protected ClientService getService() {
        return service;
    }

    @Override
    protected String getUrl() {
        return super.url + "/clientes";
    }

    @Override
    protected String getUrlWithId() {
        return getUrl() + "/{id}";
    }

    @Override
    protected String getValidJsonRequestBody() {
        return """
                {
                     "name": "Name",
                     "email": "email@gmail.com",
                     "cpf": "73396567072",
                     "password": "123456789",
                     "phone": "11999994444",
                     "cep": "01234500"
                }
                """;
    }

    @Override
    protected String getJsonWithInvalidFieldContent() {
        return """
                {
                     "name": "Name",
                     "email": "email",
                     "cpf": "73396567072",
                     "password": "123456789",
                     "phone": "11999994444",
                     "cep": "01234500"
                }
                """;
    }

    @Override
    protected String getJsonWithMissingField() {
        return """
                {
                     "name": "Name",
                     "email": "email@gmail.com",
                     "password": "123456789",
                     "phone": "11999994444",
                     "cep": "01234500"
                }
                """;
    }

    @Override
    protected String getJsonWithNullValue() {
        return """
                {
                     "name": "Name",
                     "email": "email@gmail.com",
                     "cpf": "73396567072",
                     "password": null,
                     "phone": "11999994444",
                     "cep": "01234500"
                }
                """;
    }

    @Override
    protected String getJsonWithUnprocessableRequestBody() {
        return """
                {
                     "name": Name,
                     "email": "email@gmail.com",
                     "cpf": "73396567072",
                     "password": "123456789",
                     "phone": "11999994444",
                     "cep": "01234500"
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().signClient(any(Client.class))).thenReturn(getEntity());
    }

    @Override
    protected void whenGetAllThenReturn(boolean hasContent) {
        when(getService().getAllClients()).thenReturn(hasContent? List.of(getEntity()) : List.of());
    }

    @Override
    protected void whenGetByIdThenReturn() {
        when(getService().getClientById(anyInt())).thenReturn(getEntity());
    }

    @Override
    protected void whenUpdateByIdThenReturn() {
        when(getService().updateClientById(any(Client.class), anyInt())).thenReturn(getEntity());
    }

    @Override
    protected boolean needsJwtToken() {
        return false;
    }
}