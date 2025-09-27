package sptech.school.projetoPI.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.old.core.domains.ScheduleDomain;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduleControllerTest extends ControllerTest<ScheduleDomain, ScheduleService> {

    @MockBean
    private ScheduleService service;

    @Override
    protected ScheduleDomain getEntity() {
        return new ScheduleDomain();
    }

    @Override
    protected ScheduleService getService() {
        return service;
    }

    @Override
    protected String getUrl() {
        return super.url + "/agendamentos";
    }


    @Override
    protected String getValidJsonRequestBody() {
        return """
                {
                     "clientDomain": 1,
                     "employeeDomain": 1,
                     "appointmentDatetime": "2025-12-31T23:59:59",
                     "status": "ACTIVE"
                }
                """;
    }

    @Override
    protected String getJsonWithInvalidFieldContent() {
        return """
                {
                     "clientDomain": -1,
                     "employeeDomain": 1,
                     "appointmentDatetime": "2025-12-31T23:59:59",
                     "status": "ACTIVE"
                }
                """;
    }

    @Override
    protected String getJsonWithMissingField() {
        return """
                {
                     "clientDomain": 1,
                     "appointmentDatetime": "2025-12-31T23:59:59",
                     "status": "ACTIVE"
                }
                """;
    }

    @Override
    protected String getJsonWithNullValue() {
        return """
                {
                     "clientDomain": null,
                     "employeeDomain": 1,
                     "appointmentDatetime": "2025-12-31T23:59:59",
                     "status": "ACTIVE"
                }
                """;
    }

    @Override
    protected String getJsonWithUnprocessableRequestBody() {
        return """
                {
                     "clientDomain": 1,
                     "employeeDomain": 1,
                     "appointmentDatetime": "2025-12-31T23:59:59",
                     "status": ACTIVE
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().postMethod(any(ScheduleDomain.class))).thenReturn(getEntity());
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
        when(getService().putByIdMethod(any(ScheduleDomain.class), anyInt())).thenReturn(getEntity());
    }
}