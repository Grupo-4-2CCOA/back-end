package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.entities.*;
import sptech.school.projetoPI.enums.Status;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.repositories.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

class ScheduleServiceTest extends ServiceTest {

    @InjectMocks
    private ScheduleService service;

    @Mock
    private ScheduleRepository repository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PaymentTypeRepository paymentTypeRepository;

    private final Employee employee = Employee.builder()
            .id(1)
            .name("Employee")
            .active(true)
            .build();

    private final Client client = Client.builder()
            .id(1)
            .name("Client")
            .active(true)
            .build();

    private final PaymentType paymentType = PaymentType.builder()
            .id(1)
            .name("DÉBITO")
            .active(true)
            .build();

    private final Schedule schedule = Schedule.builder()
            .id(1)
            .employee(employee)
            .client(client)
            .paymentType(paymentType)
            .duration(10)
            .status(Status.ACTIVE)
            .appointmentDatetime(LocalDateTime.now())
            .transactionHash("transactionHash")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();


    @Override
    @Test
    @DisplayName("Quando método SignSchedule() for chamado com credenciais válidas, deve retornar Schedule")
    void executeEntitySignWithValidParametersTest() {
        when(repository.existsByAppointmentDatetime(any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(paymentTypeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(schedule)).thenReturn(schedule);

        Schedule response = service.signSchedule(schedule);
        assertEquals(schedule, response);
    }

    @Test
    @DisplayName("Quando já existir Schedule no mesmo horário, método SignSchedule() deve estourar EntityConflictException")
    void executeScheduleSignWithExistingDateTimeMustThrowEntityConflictExceptionTest() {
        when(repository.existsByAppointmentDatetime(any(LocalDateTime.class))).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.signSchedule(schedule));
    }

    @Test
    @DisplayName("Quando não existir Client com ID requisitado, método SignSchedule() deve estourar RelatedEntityNotFoundException")
    void executeScheduleSignWithInvalidClientIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsByAppointmentDatetime(any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.signSchedule(schedule));
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado, método SignSchedule() deve estourar RelatedEntityNotFoundException")
    void executeScheduleSignWithInvalidEmployeeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsByAppointmentDatetime(any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.signSchedule(schedule));
    }

    @Test
    @DisplayName("Quando não existir PaymentType com ID requisitado, método SignSchedule() deve estourar RelatedEntityNotFoundException")
    void executeScheduleSignWithInvalidPaymentTypeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsByAppointmentDatetime(any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(paymentTypeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.signSchedule(schedule));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Schedules na lista, método GetAllSchedules() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<Schedule> schedules = List.of(
                Schedule.builder()
                        .id(1)
                        .status(Status.ACTIVE)
                        .build(),

                Schedule.builder()
                        .id(2)
                        .status(Status.CANCELED)
                        .build(),

                Schedule.builder()
                        .id(3)
                        .status(Status.COMPLETED)
                        .build()
        );

        when(repository.findAll()).thenReturn(schedules);

        List<Schedule> response = service.getAllSchedules();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Schedule com ID 1, método GetScheduleById() deve retornar o Schedule encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(schedule));

        Schedule response = service.getScheduleById(1);
        assertEquals(schedule, response);
    }
    
    @Override
    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método GetScheduleById() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getScheduleById(1));
    }

    @Override
    @Test
    @DisplayName("Quando método UpdateScheduleById() for chamado com credenciais válidas, deve retornar Schedule atualizado")
    void executeEntityUpdateByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Schedule()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndAppointmentDatetime(anyInt(), any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(paymentTypeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(schedule)).thenReturn(schedule);

        Schedule response = service.updateScheduleById(schedule, anyInt());
        assertEquals(schedule, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método UpdateScheduleById() deve estourar EntityNotFoundException")
    void executeEntityUpdateByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.updateScheduleById(schedule, 1));
    }

    @Test
    @DisplayName("Quando já existir Schedule no mesmo horário, método UpdateScheduleById() deve estourar EntityConflictException")
    void executeScheduleUpdateByIdWithExistingDateTimeMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndAppointmentDatetime(anyInt(), any(LocalDateTime.class))).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.updateScheduleById(schedule, 1));
    }

    @Test
    @DisplayName("Quando não existir Client com ID requisitado em Schedule, método UpdateScheduleById() deve estourar RelatedEntityNotFoundException")
    void executeScheduleUpdateByIdWithInvalidClientIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndAppointmentDatetime(anyInt(), any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.updateScheduleById(schedule, 1));
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado em Schedule, método UpdateScheduleById() deve estourar RelatedEntityNotFoundException")
    void executeScheduleUpdateByIdWithInvalidEmployeeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndAppointmentDatetime(anyInt(), any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.updateScheduleById(schedule, 1));
    }

    @Test
    @DisplayName("Quando não existir PaymentType com ID requisitado em Schedule, método UpdateScheduleById() deve estourar RelatedEntityNotFoundException")
    void executeScheduleUpdateByIdWithInvalidPaymentTypeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndAppointmentDatetime(anyInt(), any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(paymentTypeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.updateScheduleById(schedule, 1));
    }
    
    @Override
    @Test
    @DisplayName("Quando método DeleteScheduleById() for chamado com ID válido, deve definir Status para 'CANCELED'")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(schedule));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndStatus(anyInt(), any(Status.class))).thenReturn(false);

        service.deleteScheduleById(1);

        assertEquals(Status.CANCELED, schedule.getStatus());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método DeleteScheduleById() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteScheduleById(1));
    }

    @Test
    @DisplayName("Quando Schedule com ID requisitado já estiver cancelado, método DeleteScheduleById() deve estourar InactiveEntityException")
    void executeScheduleDeleteByIdWithInvalidIdMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndStatus(anyInt(), any(Status.class))).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteScheduleById(1));
    }
}