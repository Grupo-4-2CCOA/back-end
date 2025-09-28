package sptech.school.projetoPI.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.old.core.domains.ScheduleItemDomain;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduleItemControllerTest extends ControllerTest<ScheduleItemDomain, ScheduleItemService> {

    @MockBean
    private ScheduleItemService service;

    @Override
    protected ScheduleItemDomain getEntity() {
        return new ScheduleItemDomain();
    }

    @Override
    protected ScheduleItemService getService() {
        return service;
    }

    @Override
    protected String getUrl() {
        return super.url + "/itens-agendamento";
    }


    @Override
    protected String getValidJsonRequestBody() {
        return """
                {
                     "finalPrice": 10,
                     "discount": 5.5,
                     "scheduleDomain": 1,
                     "service": 1
                }
                """;
    }

    @Override
    protected String getJsonWithInvalidFieldContent() {
        return """
                {
                     "finalPrice": -10,
                     "discount": 5.5,
                     "scheduleDomain": 1,
                     "service": 1
                }
                """;
    }

    @Override
    protected String getJsonWithMissingField() {
        return """
                {
                     "discount": 5.5,
                     "scheduleDomain": 1,
                     "service": 1
                }
                """;
    }

    @Override
    protected String getJsonWithNullValue() {
        return """
                {
                     "finalPrice": 10,
                     "discount": null,
                     "scheduleDomain": 1,
                     "service": 1
                }
                """;
    }

    @Override
    protected String getJsonWithUnprocessableRequestBody() {
        return """
                {
                     "finalPrice": "ABC",
                     "discount": 5.5,
                     "scheduleDomain": 1,
                     "service": 1
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().postMethod(any(ScheduleItemDomain.class))).thenReturn(getEntity());
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
        when(getService().putByIdMethod(any(ScheduleItemDomain.class), anyInt())).thenReturn(getEntity());
    }
}