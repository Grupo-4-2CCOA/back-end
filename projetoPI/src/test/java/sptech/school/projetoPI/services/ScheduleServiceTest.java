package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.enums.Status;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
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
    @DisplayName("Quando método postMethod() for chamado com credenciais válidas, deve retornar Schedule")
    void executeEntitySignWithValidParametersTest() {
        when(repository.existsByAppointmentDatetime(any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(paymentTypeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(schedule)).thenReturn(schedule);

        Schedule response = service.postMethod(schedule);
        assertEquals(schedule, response);
    }

    @Test
    @DisplayName("Quando já existir Schedule no mesmo horário, método postMethod() deve estourar EntityConflictException")
    void executeScheduleSignWithExistingDateTimeMustThrowEntityConflictExceptionTest() {
        when(repository.existsByAppointmentDatetime(any(LocalDateTime.class))).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.postMethod(schedule));
    }

    @Test
    @DisplayName("Quando não existir Client com ID requisitado, método postMethod() deve estourar RelatedEntityNotFoundException")
    void executeScheduleSignWithInvalidClientIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsByAppointmentDatetime(any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.postMethod(schedule));
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado, método postMethod() deve estourar RelatedEntityNotFoundException")
    void executeScheduleSignWithInvalidEmployeeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsByAppointmentDatetime(any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.postMethod(schedule));
    }

    @Test
    @DisplayName("Quando não existir PaymentType com ID requisitado, método postMethod() deve estourar RelatedEntityNotFoundException")
    void executeScheduleSignWithInvalidPaymentTypeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsByAppointmentDatetime(any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(paymentTypeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.postMethod(schedule));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Schedules na lista, método getAllMethod() deve retornar tamanho 3")
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

        List<Schedule> response = service.getAllMethod();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Schedule com ID 1, método getByIdMethod() deve retornar o Schedule encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(schedule));

        Schedule response = service.getByIdMethod(1);
        assertEquals(schedule, response);
    }
    
    @Override
    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método getByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getByIdMethod(1));
    }

    @Override
    @Test
    @DisplayName("Quando método putByIdMethod() for chamado com credenciais válidas, deve retornar Schedule atualizado")
    void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Schedule()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndAppointmentDatetime(anyInt(), any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(paymentTypeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(repository.save(schedule)).thenReturn(schedule);

        Schedule response = service.putByIdMethod(schedule, anyInt());
        assertEquals(schedule, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método putByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.putByIdMethod(schedule, 1));
    }

    @Test
    @DisplayName("Quando já existir Schedule no mesmo horário, método putByIdMethod() deve estourar EntityConflictException")
    void executeSchedulePutByIdWithExistingDateTimeMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndAppointmentDatetime(anyInt(), any(LocalDateTime.class))).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.putByIdMethod(schedule, 1));
    }

    @Test
    @DisplayName("Quando não existir Client com ID requisitado em Schedule, método putByIdMethod() deve estourar RelatedEntityNotFoundException")
    void executeSchedulePutByIdWithInvalidClientIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndAppointmentDatetime(anyInt(), any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.putByIdMethod(schedule, 1));
    }

    @Test
    @DisplayName("Quando não existir Employee com ID requisitado em Schedule, método putByIdMethod() deve estourar RelatedEntityNotFoundException")
    void executeSchedulePutByIdWithInvalidEmployeeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndAppointmentDatetime(anyInt(), any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.putByIdMethod(schedule, 1));
    }

    @Test
    @DisplayName("Quando não existir PaymentType com ID requisitado em Schedule, método putByIdMethod() deve estourar RelatedEntityNotFoundException")
    void executeSchedulePutByIdWithInvalidPaymentTypeIdMustThrowRelatedEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdNotAndAppointmentDatetime(anyInt(), any(LocalDateTime.class))).thenReturn(false);
        when(clientRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(employeeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(true);
        when(paymentTypeRepository.existsByIdAndActiveTrue(anyInt())).thenReturn(false);
        assertThrows(RelatedEntityNotFoundException.class, () -> service.putByIdMethod(schedule, 1));
    }
    
    @Override
    @Test
    @DisplayName("Quando método deleteByIdMethod() for chamado com ID válido, deve definir Status para 'CANCELED'")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(schedule));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndStatus(anyInt(), any(Status.class))).thenReturn(false);

        service.deleteByIdMethod(1);

        assertEquals(Status.CANCELED, schedule.getStatus());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Schedule com ID requisitado, método deleteByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Schedule com ID requisitado já estiver cancelado, método deleteByIdMethod() deve estourar InactiveEntityException")
    void executeScheduleDeleteByIdWithInvalidIdMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndStatus(anyInt(), any(Status.class))).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteByIdMethod(1));
    }
}