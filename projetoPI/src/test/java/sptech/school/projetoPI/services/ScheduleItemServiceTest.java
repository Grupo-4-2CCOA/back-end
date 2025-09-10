package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ScheduleItemServiceTest extends ServiceTest {

    @InjectMocks
    private ScheduleItemService service;

    @Mock
    private ScheduleItemRepository repository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private ServiceRepository serviceRepository;

    private final ServiceDomain serviceEntity = ServiceDomain.builder()
            .id(1)
            .active(true)
            .name("Service")
            .build();

    private final ScheduleDomain scheduleDomain = ScheduleDomain.builder()
            .id(1)
            .status(Status.ACTIVE)
            .build();

    private final ScheduleItemDomain scheduleItemDomain = ScheduleItemDomain.builder()
            .id(1)
            .service(serviceEntity)
            .schedule(scheduleDomain)
            .discount(10.0)
            .finalPrice(10.0)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    @Override
    @Test
    @DisplayName("Quando método postMethod() for chamado com credenciais válidas, deve retornar ScheduleItem")
    void executeEntitySignWithValidParametersTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(serviceRepository.existsById(anyInt())).thenReturn(true);
        when(repository.save(scheduleItemDomain)).thenReturn(scheduleItemDomain);

        ScheduleItemDomain response = service.postMethod(scheduleItemDomain);
        assertEquals(scheduleItemDomain, response);
    }

    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método postMethod() deve estourar RelatedEntityNotFoundException")
    void executeScheduleItemSignWithInvalidScheduleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.postMethod(scheduleItemDomain));
    }

    @Test
    @DisplayName("Quando não existir Service com ID requisitado, método postMethod() deve estourar RelatedEntityNotFoundException")
    void executeScheduleItemSignWithInvalidServiceIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(serviceRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.postMethod(scheduleItemDomain));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 ScheduleItems na lista, método getAllMethod() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<ScheduleItemDomain> scheduleItemDomains = List.of(
                ScheduleItemDomain.builder()
                        .id(1)
                        .finalPrice(10.0)
                        .build(),

                ScheduleItemDomain.builder()
                        .id(2)
                        .finalPrice(20.0)
                        .build(),

                ScheduleItemDomain.builder()
                        .id(3)
                        .finalPrice(30.0)
                        .build()
        );

        when(repository.findAll()).thenReturn(scheduleItemDomains);

        List<ScheduleItemDomain> response = service.getAllMethod();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir ScheduleItem com ID 1, método getByIdMethod() deve retornar o ScheduleItem encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(scheduleItemDomain));

        ScheduleItemDomain response = service.getByIdMethod(1);
        assertEquals(scheduleItemDomain, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir ScheduleItem com ID requisitado, método getByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getByIdMethod(1));
    }

    @Override
    @Test
    @DisplayName("Quando método putByIdMethod() for chamado com credenciais válidas, deve retornar ScheduleItem atualizado")
    void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new ScheduleItemDomain()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(serviceRepository.existsById(anyInt())).thenReturn(true);
        when(repository.save(scheduleItemDomain)).thenReturn(scheduleItemDomain);

        ScheduleItemDomain response = service.putByIdMethod(scheduleItemDomain, anyInt());
        assertEquals(scheduleItemDomain, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir ScheduleItem com ID requisitado, método putByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.putByIdMethod(scheduleItemDomain, 1));
    }

    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método putByIdMethod() deve estourar RelatedEntityNotFoundException")
    void executeScheduleItemPutByIdWithInvalidScheduleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.putByIdMethod(scheduleItemDomain, 1));
    }

    @Test
    @DisplayName("Quando não existir Service com ID requisitado, método putByIdMethod() deve estourar RelatedEntityNotFoundException")
    void executeScheduleItemPutByIdWithInvalidServiceIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(serviceRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.putByIdMethod(scheduleItemDomain, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método deleteByIdMethod() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.existsById(anyInt())).thenReturn(true);

        service.deleteByIdMethod(1);

        verify(repository, times(1)).deleteById(1);
    }

    @Override
    @Test
    @DisplayName("Quando não existir ScheduleItem com ID requisitado, método deleteByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteByIdMethod(1));
    }
}