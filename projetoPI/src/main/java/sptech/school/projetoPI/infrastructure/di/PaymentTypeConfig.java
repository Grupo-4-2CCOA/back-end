package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.application.usecases.paymentType.*;
import sptech.school.projetoPI.application.usecases.paymenttype.*;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;

@Configuration
public class PaymentTypeConfig {

    @Bean
    public CreatePaymentTypeUseCase createPaymentTypeUseCase(PaymentTypeGateway paymentTypeGateway) {
        return new CreatePaymentTypeUseCase(paymentTypeGateway);
    }

    @Bean
    public GetAllPaymentTypeUseCase getAllPaymentTypesUseCase(PaymentTypeGateway paymentTypeGateway) {
        return new GetAllPaymentTypeUseCase(paymentTypeGateway);
    }

    @Bean
    public GetPaymentTypeByIdUseCase getPaymentTypeByIdUseCase(PaymentTypeGateway paymentTypeGateway) {
        return new GetPaymentTypeByIdUseCase(paymentTypeGateway);
    }

    @Bean
    public UpdatePaymentTypeByIdUseCase updatePaymentTypeByIdUseCase(PaymentTypeGateway paymentTypeGateway) {
        return new UpdatePaymentTypeByIdUseCase(paymentTypeGateway);
    }

    @Bean
    public DeletePaymentTypeByIdUseCase deletePaymentTypeByIdUseCase(PaymentTypeGateway paymentTypeGateway) {
        return new DeletePaymentTypeByIdUseCase(paymentTypeGateway);
    }
}