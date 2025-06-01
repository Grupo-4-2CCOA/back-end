package sptech.school.projetoPI.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.entities.PaymentType;
import sptech.school.projetoPI.services.PaymentTypeService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class PaymentTypeControllerTest extends ControllerTest<PaymentType, PaymentTypeService> {

    @MockBean
    private PaymentTypeService service;

    @Override
    protected PaymentType getEntity() {
        return new PaymentType();
    }

    @Override
    protected PaymentTypeService getService() {
        return service;
    }

    @Override
    protected String getUrl() {
        return super.url + "/pagamentos";
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
                     "description": "Description"
                }
                """;
    }

    @Override
    protected String getJsonWithInvalidFieldContent() {
        return """
                {
                     "name": "",
                     "description": "Description"
                }
                """;
    }

    @Override
    protected String getJsonWithMissingField() {
        return """
                {
                     "description": "Description"
                }
                """;
    }

    @Override
    protected String getJsonWithNullValue() {
        return """
                {
                     "name": null,
                     "description": "Description"
                }
                """;
    }

    @Override
    protected String getJsonWithUnprocessableRequestBody() {
        return """
                {
                     "name": Name,
                     "description": "Description"
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().signPaymentType(any(PaymentType.class))).thenReturn(getEntity());
    }

    @Override
    protected void whenGetAllThenReturn(boolean hasContent) {
        when(getService().getAllPaymentTypes()).thenReturn(hasContent? List.of(getEntity()) : List.of());
    }

    @Override
    protected void whenGetByIdThenReturn() {
        when(getService().getPaymentTypeById(anyInt())).thenReturn(getEntity());
    }

    @Override
    protected void whenUpdateByIdThenReturn() {
        when(getService().updatePaymentTypeById(any(PaymentType.class), anyInt())).thenReturn(getEntity());
    }
}