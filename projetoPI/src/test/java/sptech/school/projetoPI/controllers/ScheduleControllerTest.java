package sptech.school.projetoPI.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.services.ScheduleService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduleControllerTest extends ControllerTest<Schedule, ScheduleService> {

    @MockBean
    private ScheduleService service;

    @Override
    protected Schedule getEntity() {
        return new Schedule();
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
                     "client": 1,
                     "employee": 1,
                     "appointmentDatetime": "2025-12-31T23:59:59",
                     "status": "ACTIVE"
                }
                """;
    }

    @Override
    protected String getJsonWithInvalidFieldContent() {
        return """
                {
                     "client": -1,
                     "employee": 1,
                     "appointmentDatetime": "2025-12-31T23:59:59",
                     "status": "ACTIVE"
                }
                """;
    }

    @Override
    protected String getJsonWithMissingField() {
        return """
                {
                     "client": 1,
                     "appointmentDatetime": "2025-12-31T23:59:59",
                     "status": "ACTIVE"
                }
                """;
    }

    @Override
    protected String getJsonWithNullValue() {
        return """
                {
                     "client": null,
                     "employee": 1,
                     "appointmentDatetime": "2025-12-31T23:59:59",
                     "status": "ACTIVE"
                }
                """;
    }

    @Override
    protected String getJsonWithUnprocessableRequestBody() {
        return """
                {
                     "client": 1,
                     "employee": 1,
                     "appointmentDatetime": "2025-12-31T23:59:59",
                     "status": ACTIVE
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().postMethod(any(Schedule.class))).thenReturn(getEntity());
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
        when(getService().putByIdMethod(any(Schedule.class), anyInt())).thenReturn(getEntity());
    }
}