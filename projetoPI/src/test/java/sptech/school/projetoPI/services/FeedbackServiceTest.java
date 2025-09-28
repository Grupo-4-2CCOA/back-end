package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.old.core.domains.FeedbackDomain;
import sptech.school.projetoPI.old.core.domains.ScheduleDomain;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class FeedbackServiceTest extends ServiceTest {

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

    private final ScheduleDomain scheduleDomain = ScheduleDomain.builder()
            .id(1)
            .client(client)
            .build();

    private final FeedbackDomain feedbackDomain = FeedbackDomain.builder()
            .id(1)
            .client(client)
            .schedule(scheduleDomain)
            .rating(5)
            .comment("Ótimo Serviço!")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    @Override
    @Test
    @DisplayName("Quando método postMethod() for chamado com credenciais válidas, deve retornar Feedback")
    void executeEntitySignWithValidParametersTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(feedbackDomain)).thenReturn(feedbackDomain);

        FeedbackDomain response = service.postMethod(feedbackDomain);
        assertEquals(feedbackDomain, response);
    }

    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método postMethod() deve estourar RelatedEntityNotFoundException")
    void executeFeedbackSignWithInvalidScheduleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.postMethod(feedbackDomain));
    }

    @Test
    @DisplayName("Quando não existir Client com ID requisitado, método postMethod() deve estourar RelatedEntityNotFoundException")
    void executeFeedbackSignWithInvalidClientIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.postMethod(feedbackDomain));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Feedbacks na lista, método getAllMethod() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<FeedbackDomain> feedbackDomains = List.of(
                FeedbackDomain.builder()
                        .id(1)
                        .rating(5)
                        .build(),

                FeedbackDomain.builder()
                        .id(2)
                        .rating(2)
                        .build(),

                FeedbackDomain.builder()
                        .id(3)
                        .rating(4)
                        .build()
        );

        when(repository.findAll()).thenReturn(feedbackDomains);

        List<FeedbackDomain> response = service.getAllMethod();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Feedback com ID 1, método getByIdMethod() deve retornar o Feedback encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(feedbackDomain));

        FeedbackDomain response = service.getByIdMethod(1);
        assertEquals(feedbackDomain, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Feedback com ID requisitado, método getByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getByIdMethod(1));
    }

    @Override
    @Test
    @DisplayName("Quando método putByIdMethod() for chamado com credenciais válidas, deve retornar Feedback atualizado")
    void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new FeedbackDomain()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(feedbackDomain)).thenReturn(feedbackDomain);

        FeedbackDomain response = service.putByIdMethod(feedbackDomain, anyInt());
        assertEquals(feedbackDomain, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Feedback com ID requisitado, método putByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.putByIdMethod(feedbackDomain, 1));
    }

    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado em Feedback, método putByIdMethod() deve estourar RelatedEntityNotFoundException")
    void executeFeedbackPutByIdWithInvalidScheduleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.putByIdMethod(feedbackDomain, 1));
    }

    @Test
    @DisplayName("Quando não existir Client com ID requisitado em Feedback, método putByIdMethod() deve estourar RelatedEntityNotFoundException")
    void executeFeedbackPutByIdWithInvalidClientIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.putByIdMethod(feedbackDomain, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método deleteByIdMethod() for chamado com ID válido, deve apagar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.existsById(anyInt())).thenReturn(true);

        service.deleteByIdMethod(1);

        verify(repository, times(1)).existsById(anyInt());
        verify(repository, times(1)).deleteById(anyInt());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Feedback com ID requisitado, método deleteByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteByIdMethod(1));
    }
}