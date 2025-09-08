package sptech.school.projetoPI.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.core.domain.EmployeeDomain;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeControllerTest extends ControllerTest<EmployeeDomain, EmployeeService> {

    @MockBean
    private EmployeeService service;

    @Override
    protected EmployeeDomain getEntity() {
        return new EmployeeDomain();
    }

    @Override
    protected EmployeeService getService() {
        return service;
    }

    @Override
    protected String getUrl() {
        return super.url + "/funcionarios";
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
                     "cep": "01234500",
                     "roleDomain": 1
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
                     "cep": "01234500",
                     "roleDomain": 1
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
                     "cep": "01234500",
                     "roleDomain": 1
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
                     "cep": "01234500",
                     "roleDomain": 1
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
                     "cep": "01234500",
                     "roleDomain": 1
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().postMethod(any(EmployeeDomain.class))).thenReturn(getEntity());
    }

    @Override
    protected void whenGetAllThenReturn(boolean hasContent) {
        when(getService().getAllMethod()).thenReturn(hasContent? List.of(getEntity()) : List.of());
    }

    @Override
    protected void whenGetByIdThenReturn() {
        when(getService().getByIdMethod(anyInt())).thenReturn(getEntity());
    }

    @Override
    protected void whenUpdateByIdThenReturn() {
        when(getService().putByIdMethod(any(EmployeeDomain.class), anyInt())).thenReturn(getEntity());
    }
}