package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.entities.Availability;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.entities.Role;
import sptech.school.projetoPI.exceptions.exceptionClass.*;
import sptech.school.projetoPI.repositories.AvailabilityRepository;
import sptech.school.projetoPI.repositories.EmployeeRepository;
import sptech.school.projetoPI.repositories.RoleRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @InjectMocks
    private AvailabilityService service;

    @Mock
    private AvailabilityRepository repository;

    @Mock
    private EmployeeRepository employeeRepository;

    private final Employee employee = Employee.builder()
            .id(1)
            .name("Fabricio")
            .cpf("55511177782")
            .build();

    private final Availability availability = Availability.builder()
            .id(1)
            .employee(employee)
            .isAvailable(true)
            .day(LocalDate.now())
            .startTime(LocalTime.of(0, 0))
            .endTime(LocalTime.of(23, 59))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();


    @Test
    @DisplayName("Quando método SignAvailability() for chamado com credenciais válidas, deve retornar Availability")
    void executeAvailabilitySignWithValidParametersTest() {
        when(employeeRepository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByDayAndEmployeeId(any(LocalDate.class), anyInt())).thenReturn(false);
        when(repository.save(availability)).thenReturn(availability);

        Availability response = service.signAvailability(availability);
        assertEquals(availability, response);
    }

    @Test
    @DisplayName("Quando horário inicial for posterior ao horário final, método SignAvailability() deve estourar InvalidTimeRangeException")
    void executeAvailabilitySignWhereInitialTimeIsAfterFinalTimeMustThrowInvalidTimeRangeExceptionTest() {
        availability.setStartTime(LocalTime.of(10,  0));
        availability.setEndTime(LocalTime.of(9, 0));

        assertThrows(InvalidTimeRangeException.class, () -> service.signAvailability(availability));
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado, método SignAvailability() deve estourar RelatedEntityNotFoundException")
    void executeAvailabilitySignWithInvalidEmployeeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(employeeRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.signAvailability(availability));
    }

    @Test
    @DisplayName("Quando já existir horário registrado, método SignAvailability() deve estourar EntityConflictException")
    void executeAvailabilitySignWithExistingTimeMustThrowEntityConflictExceptionTest() {
        when(employeeRepository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByDayAndEmployeeId(any(LocalDate.class), anyInt())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.signAvailability(availability));
    }

    @Test
    @DisplayName("Quando existir 3 Availabilities na lista, método GetAllAvailabilities() deve retornar tamanho 3")
    void executeAvailabilityFindAllWithThreeEntitiesMustReturnThreeTest() {
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

        List<Availability> response = service.getAllAvailabilities();
        assertEquals(3, response.size());
    }

    @Test
    @DisplayName("Quando existir Availability com ID 1, método GetAvailabilityById() deve retornar o Availability encontrado")
    void executeAvailabilityFindByIdMustReturnAvailabilityWithIdOneTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(availability));

        Availability response = service.getAvailabilityById(1);
        assertEquals(availability, response);
    }

    @Test
    @DisplayName("Quando não existir Availability com ID requisitado, método GetAvailabilityById() deve estourar EntityNotFoundException")
    void executeAvailabilityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getAvailabilityById(1));
    }

    @Test
    @DisplayName("Quando método UpdateAvailabilityById() for chamado com credenciais válidas, deve retornar Availability atualizado")
    void executeAvailabilityUpdateByIdWithValidEntityMustReturnUpdatedAvailabilityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Availability()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(employeeRepository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndDayAndEmployeeId(anyInt(), any(LocalDate.class), anyInt())).thenReturn(false);
        when(repository.save(availability)).thenReturn(availability);

        Availability response = service.updateAvailabilityById(availability, anyInt());
        assertEquals(availability, response);
    }

    @Test
    @DisplayName("Quando horário inicial for posterior ao horário final, método UpdateAvailabilityById() deve estourar InvalidTimeRangeException")
    void executeAvailabilityUpdateByIdWhereInitialTimeIsAfterFinalTimeMustThrowInvalidTimeRangeExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);

        availability.setStartTime(LocalTime.of(10,  0));
        availability.setEndTime(LocalTime.of(9, 0));

        assertThrows(InvalidTimeRangeException.class, () -> service.updateAvailabilityById(availability, 1));
    }

    @Test
    @DisplayName("Quando não existir Availability com ID requisitado, método UpdateAvailabilityById() deve estourar EntityNotFoundException")
    void executeAvailabilityUpdateByIdWithoutValidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.updateAvailabilityById(availability, 1));
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado em Availability, método UpdateAvailabilityById() deve estourar RelatedEntityNotFoundException")
    void executeAvailabilityUpdateByIdWithInvalidEmployeeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(employeeRepository.existsById(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.updateAvailabilityById(availability, 1));
    }

    @Test
    @DisplayName("Quando já existir horário registrado, método UpdateAvailabilityById() deve estourar EntityConflictException")
    void executeAvailabilityUpdateByIdWithExistingTimeMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(employeeRepository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndDayAndEmployeeId(anyInt(), any(LocalDate.class), anyInt())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.updateAvailabilityById(availability, 1));
    }

    @Test
    @DisplayName("Quando método DeleteAvailabilityById() for chamado com ID válido, deve inativar entidade")
    void executeAvailabilityDeleteByIdWithValidIdMustInactiveEntityTest() {
        when(repository.existsById(anyInt())).thenReturn(true);

        service.deleteAvailabilityById(1);

        verify(repository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Quando não existir Availability com ID requisitado, método DeleteAvailabilityById() deve estourar EntityNotFoundException")
    void executeAvailabilityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteAvailabilityById(1));
    }
}