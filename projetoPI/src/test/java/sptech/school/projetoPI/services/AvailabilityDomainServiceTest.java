package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.old.core.domains.EmployeeDomain;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.InvalidTimeRangeException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.repositories.EmployeeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AvailabilityDomainServiceTest extends ServiceTest {

    @InjectMocks
    private AvailabilityService service;

    @Mock
    private AvailabilityRepository repository;

    @Mock
    private EmployeeRepository employeeRepository;

    private final EmployeeDomain employeeDomain = EmployeeDomain.builder()
            .id(1)
            .name("Fabricio")
            .cpf("55511177782")
            .build();

    private final Availability availability = Availability.builder()
            .id(1)
            .employee(employeeDomain)
            .isAvailable(true)
            .day(LocalDate.now())
            .startTime(LocalTime.of(0, 0))
            .endTime(LocalTime.of(23, 59))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    @Override
    @Test
    @DisplayName("Quando método postMethod() for chamado com credenciais válidas, deve retornar Availability")
    void executeEntitySignWithValidParametersTest() {
        when(employeeRepository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByDayAndEmployeeId(any(LocalDate.class), anyInt())).thenReturn(false);
        when(repository.save(availability)).thenReturn(availability);

        Availability response = service.postMethod(availability);
        assertEquals(availability, response);
    }

    @Test
    @DisplayName("Quando horário inicial for posterior ao horário final, método postMethod() deve estourar InvalidTimeRangeException")
    void executeAvailabilitySignWhereInitialTimeIsAfterFinalTimeMustThrowInvalidTimeRangeExceptionTest() {
        availability.setStartTime(LocalTime.of(10,  0));
        availability.setEndTime(LocalTime.of(9, 0));

        assertThrows(InvalidTimeRangeException.class, () -> service.postMethod(availability));
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado, método postMethod() deve estourar RelatedEntityNotFoundException")
    void executeAvailabilitySignWithInvalidEmployeeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(employeeRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.postMethod(availability));
    }

    @Test
    @DisplayName("Quando já existir horário registrado, método postMethod() deve estourar EntityConflictException")
    void executeAvailabilitySignWithExistingTimeMustThrowEntityConflictExceptionTest() {
        when(employeeRepository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByDayAndEmployeeId(any(LocalDate.class), anyInt())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.postMethod(availability));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Availabilities na lista, método getAllMethod() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<Availability> availabilities = List.of(
                Availability.builder()
                        .id(1)
                        .day(LocalDate.now())
                        .build(),

                Availability.builder()
                        .id(2)
                        .day(LocalDate.now())
                        .build(),

                Availability.builder()
                        .id(3)
                        .day(LocalDate.now())
                        .build()
        );

        when(repository.findAll()).thenReturn(availabilities);

        List<Availability> response = service.getAllMethod();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Availability com ID 1, método getByIdMethod() deve retornar o Availability encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(availability));

        Availability response = service.getByIdMethod(1);
        assertEquals(availability, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Availability com ID requisitado, método getByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getByIdMethod(1));
    }

    @Override
    @Test
    @DisplayName("Quando método putByIdMethod() for chamado com credenciais válidas, deve retornar Availability atualizado")
    void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Availability()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(employeeRepository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndDayAndEmployeeId(anyInt(), any(LocalDate.class), anyInt())).thenReturn(false);
        when(repository.save(availability)).thenReturn(availability);

        Availability response = service.putByIdMethod(availability, anyInt());
        assertEquals(availability, response);
    }

    @Test
    @DisplayName("Quando horário inicial for posterior ao horário final, método putByIdMethod() deve estourar InvalidTimeRangeException")
    void executeAvailabilityPutByIdWhereInitialTimeIsAfterFinalTimeMustThrowInvalidTimeRangeExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);

        availability.setStartTime(LocalTime.of(10,  0));
        availability.setEndTime(LocalTime.of(9, 0));

        assertThrows(InvalidTimeRangeException.class, () -> service.putByIdMethod(availability, 1));
    }

    @Override
    @Test
    @DisplayName("Quando não existir Availability com ID requisitado, método putByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.putByIdMethod(availability, 1));
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado em Availability, método putByIdMethod() deve estourar RelatedEntityNotFoundException")
    void executeAvailabilityPutByIdWithInvalidEmployeeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(employeeRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.putByIdMethod(availability, 1));
    }

    @Test
    @DisplayName("Quando já existir horário registrado, método putByIdMethod() deve estourar EntityConflictException")
    void executeAvailabilityPutByIdWithExistingTimeMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(employeeRepository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndDayAndEmployeeId(anyInt(), any(LocalDate.class), anyInt())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.putByIdMethod(availability, 1));
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
    @DisplayName("Quando não existir Availability com ID requisitado, método deleteByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteByIdMethod(1));
    }
}