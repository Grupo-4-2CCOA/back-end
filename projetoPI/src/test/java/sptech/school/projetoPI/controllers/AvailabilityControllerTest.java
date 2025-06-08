package sptech.school.projetoPI.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.entities.Availability;
import sptech.school.projetoPI.services.AvailabilityService;

import java.util.List;

@SpringBootTest
class AvailabilityControllerTest extends ControllerTest<Availability, AvailabilityService> {

    @MockBean
    private AvailabilityService service;

    @Override
    protected Availability getEntity() {
        return new Availability();
    }

    @Override
    protected AvailabilityService getService() {
        return service;
    }

    @Override
    protected String getUrl() {
        return super.url + "/disponibilidades";
    }

    @Override
    protected String getValidJsonRequestBody() {
        return """
                {
                     "day": "2026-01-01",
                     "startTime": "00:00:00",
                     "endTime": "23:59:59",
                     "employee": 1
                }
                """;
    }

    @Override
    protected String getJsonWithInvalidFieldContent() {
        return """
                {
                     "day": 2026,
                     "startTime": "00:00:00",
                     "endTime": "23:59:59",
                     "employee": 1
                }
                """;
    }

    @Override
    protected String getJsonWithMissingField() {
        return """
                {
                     "startTime": "00:00:00",
                     "endTime": "23:59:59",
                     "employee": 1
                }
                """;
    }

    @Override
    protected String getJsonWithNullValue() {
        return """
                {
                     "day": null,
                     "startTime": "00:00:00",
                     "endTime": "23:59:59",
                     "employee": 1
                }
                """;
    }

    @Override
    protected String getJsonWithUnprocessableRequestBody() {
        return """
                {
                     "day": "2026-02-29",
                     "startTime": "00:00:00",
                     "endTime": "23:59:59",
                     "employee": 1
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().postMethod(any(Availability.class))).thenReturn(getEntity());
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
        when(getService().putByIdMethod(any(Availability.class), anyInt())).thenReturn(getEntity());
    }
}