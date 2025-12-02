package sptech.school.projetoPI.usecases.feedback;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.dto.feedback.FeedbackRequestDto;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.feedback.CreateFeedbackUseCase;
import sptech.school.projetoPI.core.domains.FeedbackDomain;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.gateways.FeedbackGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFeedbackUseCaseTest {

    @Mock
    private FeedbackGateway feedbackGateway;

    @Mock
    private ScheduleGateway scheduleGateway;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private CreateFeedbackUseCase createFeedbackUseCase;

    @Test
    @DisplayName("Deve criar feedback com sucesso quando dados são válidos")
    void deveCriarFeedbackComSucesso() {
        // Arrange
        Integer scheduleId = 1;
        ScheduleDomain schedule = new ScheduleDomain();
        schedule.setId(scheduleId);

        FeedbackRequestDto requestDto = new FeedbackRequestDto();
        requestDto.setScheduleId(scheduleId);
        requestDto.setComment("Ótimo atendimento");
        requestDto.setRating(5);

        when(scheduleGateway.findById(scheduleId)).thenReturn(Optional.of(schedule));

        // Act
        FeedbackDomain result = createFeedbackUseCase.execute(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals("Ótimo atendimento", result.getComment());
        assertEquals(5, result.getRating());
        assertNotNull(result.getScheduleDomain());
        verify(scheduleGateway, times(1)).findById(scheduleId);
    }

    @Test
    @DisplayName("Deve lançar RelatedEntityNotFoundException quando agendamento não existe")
    void deveLancarExcecaoQuandoAgendamentoNaoExiste() {
        // Arrange
        Integer scheduleId = 999;
        FeedbackRequestDto requestDto = new FeedbackRequestDto();
        requestDto.setScheduleId(scheduleId);

        when(scheduleGateway.findById(scheduleId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RelatedEntityNotFoundException.class, () -> {
            createFeedbackUseCase.execute(requestDto);
        });

        verify(scheduleGateway, times(1)).findById(scheduleId);
    }
}

