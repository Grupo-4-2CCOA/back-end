package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.entities.ScheduleItem;
import sptech.school.projetoPI.entities.Service;
import sptech.school.projetoPI.enums.Status;
import sptech.school.projetoPI.exceptions.exceptionClass.*;
import sptech.school.projetoPI.repositories.ScheduleItemRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;
import sptech.school.projetoPI.repositories.ServiceRepository;

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

    private final Service serviceEntity = Service.builder()
            .id(1)
            .active(true)
            .name("Service")
            .build();

    private final Schedule schedule = Schedule.builder()
            .id(1)
            .status(Status.ACTIVE)
            .build();

    private final ScheduleItem scheduleItem = ScheduleItem.builder()
            .id(1)
            .service(serviceEntity)
            .schedule(schedule)
            .discount(10.0)
            .finalPrice(10.0)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    @Override
    @Test
    @DisplayName("Quando método SignScheduleItem() for chamado com credenciais válidas, deve retornar ScheduleItem")
    void executeEntitySignWithValidParametersTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(serviceRepository.existsById(anyInt())).thenReturn(true);
        when(repository.save(scheduleItem)).thenReturn(scheduleItem);

        ScheduleItem response = service.signScheduleItem(scheduleItem);
        assertEquals(scheduleItem, response);
    }

    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método SignScheduleItem() deve estourar RelatedEntityNotFoundException")
    void executeScheduleItemSignWithInvalidScheduleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.signScheduleItem(scheduleItem));
    }

    @Test
    @DisplayName("Quando não existir Service com ID requisitado, método SignScheduleItem() deve estourar RelatedEntityNotFoundException")
    void executeScheduleItemSignWithInvalidServiceIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(serviceRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.signScheduleItem(scheduleItem));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 ScheduleItems na lista, método GetAllScheduleItems() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<ScheduleItem> scheduleItems = List.of(
                ScheduleItem.builder()
                        .id(1)
                        .finalPrice(10.0)
                        .build(),

                ScheduleItem.builder()
                        .id(2)
                        .finalPrice(20.0)
                        .build(),

                ScheduleItem.builder()
                        .id(3)
                        .finalPrice(30.0)
                        .build()
        );

        when(repository.findAll()).thenReturn(scheduleItems);

        List<ScheduleItem> response = service.getAllScheduleItems();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir ScheduleItem com ID 1, método GetScheduleItemById() deve retornar o ScheduleItem encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(scheduleItem));

        ScheduleItem response = service.getScheduleItemById(1);
        assertEquals(scheduleItem, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir ScheduleItem com ID requisitado, método GetScheduleItemById() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getScheduleItemById(1));
    }

    @Override
    @Test
    @DisplayName("Quando método UpdateScheduleItemById() for chamado com credenciais válidas, deve retornar ScheduleItem atualizado")
    void executeEntityUpdateByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new ScheduleItem()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(serviceRepository.existsById(anyInt())).thenReturn(true);
        when(repository.save(scheduleItem)).thenReturn(scheduleItem);

        ScheduleItem response = service.updateScheduleItemById(scheduleItem, anyInt());
        assertEquals(scheduleItem, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir ScheduleItem com ID requisitado, método UpdateScheduleItemById() deve estourar EntityNotFoundException")
    void executeEntityUpdateByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.updateScheduleItemById(scheduleItem, 1));
    }

    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método UpdateScheduleItemById() deve estourar RelatedEntityNotFoundException")
    void executeScheduleItemUpdateByIdWithInvalidScheduleIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.updateScheduleItemById(scheduleItem, 1));
    }

    @Test
    @DisplayName("Quando não existir Service com ID requisitado, método UpdateScheduleItemById() deve estourar RelatedEntityNotFoundException")
    void executeScheduleItemUpdateByIdWithInvalidServiceIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(scheduleRepository.existsById(anyInt())).thenReturn(true);
        when(serviceRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.updateScheduleItemById(scheduleItem, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método DeleteScheduleItemById() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.existsById(anyInt())).thenReturn(true);

        service.deleteScheduleItemById(1);

        verify(repository, times(1)).deleteById(1);
    }

    @Override
    @Test
    @DisplayName("Quando não existir ScheduleItem com ID requisitado, método DeleteScheduleItemById() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteScheduleItemById(1));
    }
}