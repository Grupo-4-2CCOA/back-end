package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.entities.PaymentType;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.PaymentTypeRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PaymentTypeServiceTest extends ServiceTest {

    @InjectMocks
    private PaymentTypeService service;

    @Mock
    private PaymentTypeRepository repository;

    @Mock
    private ScheduleRepository scheduleRepository;

    private final PaymentType paymentType = PaymentType.builder()
            .id(1)
            .name("DEBITO")
            .description("Pagamento no Débito")
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    @Override
    @Test
    @DisplayName("Quando método SignPaymentType() for chamado com credenciais válidas, deve retornar PaymentType")
    void executeEntitySignWithValidParametersTest() {
        when(repository.existsByNameIgnoreCase(anyString())).thenReturn(false);
        when(repository.save(paymentType)).thenReturn(paymentType);

        PaymentType response = service.signPaymentType(paymentType);
        assertEquals(paymentType, response);
    }

    @Test
    @DisplayName("Quando já existir PaymentType com o mesmo nome, método SignPaymentType() deve estourar EntityConflictException")
    void executePaymentTypeSignWithExistingPaymentTypeNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsByNameIgnoreCase(anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.signPaymentType(paymentType));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 PaymentTypes na lista, método GetAllPaymentTypes() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<PaymentType> paymentTypes = List.of(
                PaymentType.builder()
                        .id(1)
                        .name("Manicure")
                        .build(),

                PaymentType.builder()
                        .id(2)
                        .name("Depilação")
                        .build(),

                PaymentType.builder()
                        .id(3)
                        .name("Hidratação")
                        .build()
        );

        when(repository.findAllByActiveTrue()).thenReturn(paymentTypes);

        List<PaymentType> response = service.getAllPaymentTypes();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir PaymentType com ID 1, método GetPaymentTypeById() deve retornar o PaymentType encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(paymentType));

        PaymentType response = service.getPaymentTypeById(1);
        assertEquals(paymentType, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir PaymentType com ID requisitado, método GetPaymentTypeById() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getPaymentTypeById(1));
    }

    @Override
    @Test
    @DisplayName("Quando método UpdatePaymentTypeById() for chamado com credenciais válidas, deve retornar PaymentType atualizado")
    void executeEntityUpdateByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new PaymentType()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndNameIgnoreCase(anyInt(), anyString())).thenReturn(false);
        when(repository.save(paymentType)).thenReturn(paymentType);

        PaymentType response = service.updatePaymentTypeById(paymentType, anyInt());
        assertEquals(paymentType, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir PaymentType com ID requisitado, método UpdatePaymentTypeById() deve estourar EntityNotFoundException")
    void executeEntityUpdateByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.updatePaymentTypeById(paymentType, 1));
    }

    @Test
    @DisplayName("Quando PaymentType com ID requisitado estiver inativo, método UpdatePaymentTypeById() deve estourar InactiveEntityException")
    void executePaymentTypeUpdateByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.updatePaymentTypeById(paymentType, 1));
    }

    @Test
    @DisplayName("Quando nome de PaymentType já estiver registrado, método UpdatePaymentTypeById() deve estourar EntityConflictException")
    void executePaymentTypeUpdateByIdWithExistingPaymentTypeNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndNameIgnoreCase(anyInt(), anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.updatePaymentTypeById(paymentType, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método DeletePaymentTypeById() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(paymentType));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByPaymentTypeId(anyInt())).thenReturn(false);

        service.deletePaymentTypeById(1);

        assertFalse(paymentType.getActive());
    }

    @Override
    @Test
    @DisplayName("Quando não existir PaymentType com ID requisitado, método DeletePaymentTypeById() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deletePaymentTypeById(1));
    }

    @Test
    @DisplayName("Quando PaymentType com ID requisitado estiver inativo, método DeletePaymentTypeById() deve estourar InactiveEntityException")
    void executePaymentTypeDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deletePaymentTypeById(1));
    }

    @Test
    @DisplayName("Quando PaymentType com ID requisitado estiver registrado em um Schedule, método DeletePaymentTypeById() deve estourar ForeignKeyConstraintException")
    void executePaymentTypeDeleteByIdWithScheduleForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByPaymentTypeId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deletePaymentTypeById(1));
    }
}