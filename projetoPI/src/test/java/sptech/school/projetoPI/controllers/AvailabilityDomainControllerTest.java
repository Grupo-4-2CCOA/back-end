package sptech.school.projetoPI.controllers;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.core.domains.AvailabilityDomain;

import java.util.List;

@SpringBootTest
class AvailabilityDomainControllerTest extends ControllerTest<AvailabilityDomain, AvailabilityService> {

    @MockBean
    private AvailabilityService service;

    @Override
    protected AvailabilityDomain getEntity() {
        return new AvailabilityDomain();
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
                     "employeeDomain": 1
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
                     "employeeDomain": 1
                }
                """;
    }

    @Override
    protected String getJsonWithMissingField() {
        return """
                {
                     "startTime": "00:00:00",
                     "endTime": "23:59:59",
                     "employeeDomain": 1
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
                     "employeeDomain": 1
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
                     "employeeDomain": 1
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().postMethod(any(AvailabilityDomain.class))).thenReturn(getEntity());
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
        when(getService().putByIdMethod(any(AvailabilityDomain.class), anyInt())).thenReturn(getEntity());
    }
}