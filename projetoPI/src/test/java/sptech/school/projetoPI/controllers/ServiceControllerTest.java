package sptech.school.projetoPI.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.core.domains.Service;
import sptech.school.projetoPI.services.ServiceService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ServiceControllerTest extends ControllerTest<Service, ServiceService> {

    @MockBean
    private ServiceService service;

    @Override
    protected Service getEntity() {
        return new Service();
    }

    @Override
    protected ServiceService getService() {
        return service;
    }

    @Override
    protected String getUrl() {
        return super.url + "/servicos";
    }


    @Override
    protected String getValidJsonRequestBody() {
        return """
                {
                     "name": "Name",
                     "description": "Description",
                     "basePrice": 50,
                     "baseDuration": 10,
                     "image": "Image",
                     "category": 1
                }
                """;
    }

    @Override
    protected String getJsonWithInvalidFieldContent() {
        return """
                {
                     "name": "Name",
                     "description": "Description",
                     "basePrice": -50,
                     "baseDuration": 10,
                     "image": "Image",
                     "category": 1
                }
                """;
    }

    @Override
    protected String getJsonWithMissingField() {
        return """
                {
                     "description": "Description",
                     "basePrice": 50,
                     "baseDuration": 10,
                     "image": "Image",
                     "category": 1
                }
                """;
    }

    @Override
    protected String getJsonWithNullValue() {
        return """
                {
                     "name": null,
                     "description": "Description",
                     "basePrice": 50,
                     "baseDuration": 10,
                     "image": "Image",
                     "category": 1
                }
                """;
    }

    @Override
    protected String getJsonWithUnprocessableRequestBody() {
        return """
                {
                     "name": Name,
                     "description": "Description",
                     "basePrice": 50,
                     "baseDuration": 10,
                     "image": "Image",
                     "category": 1
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().postMethod(any(Service.class))).thenReturn(getEntity());
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
        when(getService().putByIdMethod(any(Service.class), anyInt())).thenReturn(getEntity());
    }
}