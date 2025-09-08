package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.core.application.usecases.paymentType.*;
import sptech.school.projetoPI.core.gateways.PaymentTypeGateway;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

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
    public DeletePaymentTypeByIdUseCase deletePaymentTypeByIdUseCase(PaymentTypeGateway paymentTypeGateway, ScheduleGateway scheduleGateway) {
        return new DeletePaymentTypeByIdUseCase(paymentTypeGateway,scheduleGateway);
    }
}