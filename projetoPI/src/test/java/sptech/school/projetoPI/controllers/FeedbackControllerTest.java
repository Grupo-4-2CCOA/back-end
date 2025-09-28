package sptech.school.projetoPI.controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sptech.school.projetoPI.old.core.domains.FeedbackDomain;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class FeedbackControllerTest extends ControllerTest<FeedbackDomain, FeedbackService> {

    @MockBean
    private FeedbackService service;

    @Override
    protected FeedbackDomain getEntity() {
        return new FeedbackDomain();
    }

    @Override
    protected FeedbackService getService() {
        return service;
    }

    @Override
    protected String getUrl() {
        return super.url + "/feedbacks";
    }


    @Override
    protected String getValidJsonRequestBody() {
        return """
                {
                     "comment": "Comment",
                     "rating": 5,
                     "scheduleDomain": 1,
                     "clientDomain": 1
                }
                """;
    }

    @Override
    protected String getJsonWithInvalidFieldContent() {
        return """
                {
                     "comment": "Comment",
                     "rating": -5,
                     "scheduleDomain": 1,
                     "clientDomain": 1
                }
                """;
    }

    @Override
    protected String getJsonWithMissingField() {
        return """
                {
                     "comment": "Comment",
                     "scheduleDomain": 1,
                     "clientDomain": 1
                }
                """;
    }

    @Override
    protected String getJsonWithNullValue() {
        return """
                {
                     "comment": "Comment",
                     "rating": 5,
                     "scheduleDomain": null,
                     "clientDomain": 1
                }
                """;
    }

    @Override
    protected String getJsonWithUnprocessableRequestBody() {
        return """
                {
                     "comment": "Comment",
                     "rating": "A",
                     "scheduleDomain": 1,
                     "clientDomain": 1
                }
                """;
    }

    @Override
    protected void whenSignThenReturn() {
        when(getService().postMethod(any(FeedbackDomain.class))).thenReturn(getEntity());
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
        when(getService().putByIdMethod(any(FeedbackDomain.class), anyInt())).thenReturn(getEntity());
    }
}