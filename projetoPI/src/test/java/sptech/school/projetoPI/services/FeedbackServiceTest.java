package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.exceptions.exceptionClass.*;
import sptech.school.projetoPI.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService service;

    @Mock
    private FeedbackRepository repository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ClientRepository clientRepository;

    private final Client client = Client.builder()
            .id(1)
            .name("Fabricio")
            .cpf("11122233345")
            .build();

    private final Schedule schedule = Schedule.builder()
            .id(1)
            .client(client)
            .build();

    private final Feedback feedback = Feedback.builder()
            .id(1)
            .client(client)
            .schedule(schedule)
            .rating(5)
            .comment("Ótimo Serviço!")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();


    @Test
    @DisplayName("Quando método SignFeedback() for chamado com credenciais válidas, deve retornar Feedback")
    void executeFeedbackSignWithValidParametersTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(feedback)).thenReturn(feedback);

        Feedback response = service.signFeedback(feedback);
        assertEquals(feedback, response);
    }

    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método SignFeedback() deve estourar RelatedEntityNotFoundException")
    void executeFeedbackSignWithInvalidScheduleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.signFeedback(feedback));
    }

    @Test
    @DisplayName("Quando não existir Client com ID requisitado, método SignFeedback() deve estourar RelatedEntityNotFoundException")
    void executeFeedbackSignWithInvalidClientIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.signFeedback(feedback));
    }

    @Test
    @DisplayName("Quando existir 3 Feedbacks na lista, método GetAllFeedbacks() deve retornar tamanho 3")
    void executeFeedbackFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<Feedback> feedbacks = List.of(
                Feedback.builder()
                        .id(1)
                        .rating(5)
                        .build(),

                Feedback.builder()
                        .id(2)
                        .rating(2)
                        .build(),

                Feedback.builder()
                        .id(3)
                        .rating(4)
                        .build()
        );

        when(repository.findAll()).thenReturn(feedbacks);

        List<Feedback> response = service.getAllFeedbacks();
        assertEquals(3, response.size());
    }

    @Test
    @DisplayName("Quando existir Feedback com ID 1, método GetFeedbackById() deve retornar o Feedback encontrado")
    void executeFeedbackFindByIdMustReturnFeedbackWithIdOneTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(feedback));

        Feedback response = service.getFeedbackById(1);
        assertEquals(feedback, response);
    }

    @Test
    @DisplayName("Quando não existir Feedback com ID requisitado, método GetFeedbackById() deve estourar EntityNotFoundException")
    void executeFeedbackFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getFeedbackById(1));
    }

    @Test
    @DisplayName("Quando método UpdateFeedbackById() for chamado com credenciais válidas, deve retornar Feedback atualizado")
    void executeFeedbackUpdateByIdWithValidEntityMustReturnUpdatedFeedbackTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Feedback()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(feedback)).thenReturn(feedback);

        Feedback response = service.updateFeedbackById(feedback, anyInt());
        assertEquals(feedback, response);
    }

    @Test
    @DisplayName("Quando não existir Feedback com ID requisitado, método UpdateFeedbackById() deve estourar EntityNotFoundException")
    void executeFeedbackUpdateByIdWithoutValidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.updateFeedbackById(feedback, 1));
    }

    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado em Feedback, método UpdateFeedbackById() deve estourar RelatedEntityNotFoundException")
    void executeFeedbackUpdateByIdWithInvalidScheduleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.updateFeedbackById(feedback, 1));
    }

    @Test
    @DisplayName("Quando não existir Client com ID requisitado em Feedback, método UpdateFeedbackById() deve estourar RelatedEntityNotFoundException")
    void executeFeedbackUpdateByIdWithInvalidClientIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.updateFeedbackById(feedback, 1));
    }

    @Test
    @DisplayName("Quando método DeleteFeedbackById() for chamado com ID válido, deve apagar entidade")
    void executeFeedbackDeleteByIdWithValidIdMustInactiveEntityTest() {
        when(repository.existsById(anyInt())).thenReturn(true);

        service.deleteFeedbackById(1);

        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Test
    @DisplayName("Quando não existir Feedback com ID requisitado, método DeleteFeedbackById() deve estourar EntityNotFoundException")
    void executeFeedbackDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteFeedbackById(1));
    }
}