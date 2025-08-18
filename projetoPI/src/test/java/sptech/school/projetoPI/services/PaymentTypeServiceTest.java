package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import sptech.school.projetoPI.core.domains.PaymentType;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.InactiveEntityException;

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
    @DisplayName("Quando método postMethod() for chamado com credenciais válidas, deve retornar PaymentType")
    void executeEntitySignWithValidParametersTest() {
        when(repository.existsByNameIgnoreCase(anyString())).thenReturn(false);
        when(repository.save(paymentType)).thenReturn(paymentType);

        PaymentType response = service.postMethod(paymentType);
        assertEquals(paymentType, response);
    }

    @Test
    @DisplayName("Quando já existir PaymentType com o mesmo nome, método postMethod() deve estourar EntityConflictException")
    void executePaymentTypeSignWithExistingPaymentTypeNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsByNameIgnoreCase(anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.postMethod(paymentType));
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 PaymentTypes na lista, método getAllMethod() deve retornar tamanho 3")
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

        List<PaymentType> response = service.getAllMethod();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir PaymentType com ID 1, método getByIdMethod() deve retornar o PaymentType encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(paymentType));

        PaymentType response = service.getByIdMethod(1);
        assertEquals(paymentType, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir PaymentType com ID requisitado, método getByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getByIdMethod(1));
    }

    @Override
    @Test
    @DisplayName("Quando método putByIdMethod() for chamado com credenciais válidas, deve retornar PaymentType atualizado")
    void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new PaymentType()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndNameIgnoreCase(anyInt(), anyString())).thenReturn(false);
        when(repository.save(paymentType)).thenReturn(paymentType);

        PaymentType response = service.putByIdMethod(paymentType, anyInt());
        assertEquals(paymentType, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir PaymentType com ID requisitado, método putByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.putByIdMethod(paymentType, 1));
    }

    @Test
    @DisplayName("Quando PaymentType com ID requisitado estiver inativo, método putByIdMethod() deve estourar InactiveEntityException")
    void executePaymentTypePutByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.putByIdMethod(paymentType, 1));
    }

    @Test
    @DisplayName("Quando nome de PaymentType já estiver registrado, método putByIdMethod() deve estourar EntityConflictException")
    void executePaymentTypePutByIdWithExistingPaymentTypeNameMustThrowEntityConflictExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.existsByIdNotAndNameIgnoreCase(anyInt(), anyString())).thenReturn(true);
        assertThrows(EntityConflictException.class, () -> service.putByIdMethod(paymentType, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método deleteByIdMethod() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(paymentType));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByPaymentTypeId(anyInt())).thenReturn(false);

        service.deleteByIdMethod(1);

        assertFalse(paymentType.getActive());
    }

    @Override
    @Test
    @DisplayName("Quando não existir PaymentType com ID requisitado, método deleteByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando PaymentType com ID requisitado estiver inativo, método deleteByIdMethod() deve estourar InactiveEntityException")
    void executePaymentTypeDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando PaymentType com ID requisitado estiver registrado em um Schedule, método deleteByIdMethod() deve estourar ForeignKeyConstraintException")
    void executePaymentTypeDeleteByIdWithScheduleForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByPaymentTypeId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteByIdMethod(1));
    }
}