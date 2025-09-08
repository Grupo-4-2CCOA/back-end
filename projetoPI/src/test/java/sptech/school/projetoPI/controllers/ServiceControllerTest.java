package sptech.school.projetoPI.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.core.domain.ServiceDomain;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ServiceControllerTest extends ControllerTest<ServiceDomain, ServiceService> {

    @MockBean
    private ServiceService service;

    @Override
    protected ServiceDomain getEntity() {
        return new ServiceDomain();
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
                     "categoryDomain": 1
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
                     "categoryDomain": 1
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
                     "categoryDomain": 1
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
                     "categoryDomain": 1
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
                     "categoryDomain": 1
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().postMethod(any(ServiceDomain.class))).thenReturn(getEntity());
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
        when(getService().putByIdMethod(any(ServiceDomain.class), anyInt())).thenReturn(getEntity());
    }
}