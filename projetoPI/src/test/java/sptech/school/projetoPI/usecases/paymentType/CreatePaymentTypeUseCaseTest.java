package sptech.school.projetoPI.usecases.paymentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.paymentType.CreatePaymentTypeUseCase;
import sptech.school.projetoPI.core.domains.PaymentTypeDomain;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePaymentTypeUseCaseTest {

    @Mock
    private PaymentTypeGateway paymentTypeGateway;

    @InjectMocks
    private CreatePaymentTypeUseCase createPaymentTypeUseCase;

    @Test
    @DisplayName("Deve criar tipo de pagamento com sucesso quando dados são válidos")
    void deveCriarPaymentTypeComSucesso() {
        // Arrange
        PaymentTypeDomain paymentType = new PaymentTypeDomain();
        paymentType.setName("Cartão");
        paymentType.setDescription("Cartão de crédito");
        paymentType.setActive(true);

        PaymentTypeDomain savedPaymentType = new PaymentTypeDomain(1, true, LocalDateTime.now(), LocalDateTime.now(),
                "Cartão", "Cartão de crédito");

        when(paymentTypeGateway.existsByNameIgnoreCase(anyString())).thenReturn(false);
        when(paymentTypeGateway.save(any(PaymentTypeDomain.class))).thenReturn(savedPaymentType);

        // Act
        PaymentTypeDomain result = createPaymentTypeUseCase.execute(paymentType);

        // Assert
        assertNotNull(result);
        assertEquals(savedPaymentType.getId(), result.getId());
        verify(paymentTypeGateway, times(1)).existsByNameIgnoreCase(anyString());
        verify(paymentTypeGateway, times(1)).save(any(PaymentTypeDomain.class));
    }

    @Test
    @DisplayName("Deve lançar EntityConflictException quando tipo de pagamento com mesmo nome já existe")
    void deveLancarExcecaoQuandoPaymentTypeJaExiste() {
        // Arrange
        PaymentTypeDomain paymentType = new PaymentTypeDomain();
        paymentType.setName("Cartão");

        when(paymentTypeGateway.existsByNameIgnoreCase(anyString())).thenReturn(true);

        // Act & Assert
        assertThrows(EntityConflictException.class, () -> {
            createPaymentTypeUseCase.execute(paymentType);
        });

        verify(paymentTypeGateway, times(1)).existsByNameIgnoreCase(anyString());
        verify(paymentTypeGateway, never()).save(any(PaymentTypeDomain.class));
    }
}

