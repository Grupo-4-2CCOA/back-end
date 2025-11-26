package sptech.school.projetoPI.core.application.usecases.schedule;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;
import sptech.school.projetoPI.core.domains.*;
import sptech.school.projetoPI.core.gateways.*;

import java.time.LocalDateTime;

public class CreateScheduleUseCase {

    private final ScheduleGateway scheduleGateway;
    private final UserGateway userGateway;
    private final PaymentTypeGateway paymentTypeGateway;
    private final ServiceGateway serviceGateway;
    private final CalendarGateway calendarGateway;

    public CreateScheduleUseCase(ScheduleGateway scheduleGateway, UserGateway userGateway, PaymentTypeGateway paymentTypeGateway, ServiceGateway serviceGateway, CalendarGateway calendarGateway) {
        this.scheduleGateway = scheduleGateway;
        this.userGateway = userGateway;
        this.paymentTypeGateway = paymentTypeGateway;
        this.serviceGateway = serviceGateway;
        this.calendarGateway = calendarGateway;
    }

    public ScheduleDomain execute(ScheduleDomain scheduleDomain) throws Exception {
        if (scheduleDomain.getClientDomain() != null && scheduleDomain.getClientDomain().getId() != null) {
            UserDomain client = userGateway.findById(scheduleDomain.getClientDomain().getId())
                    .orElseThrow(() -> new RelatedEntityNotFoundException(
                            "Cliente com ID " + scheduleDomain.getClientDomain().getId() + " não encontrado"));
            scheduleDomain.setClientDomain(client);
        }
        if (scheduleDomain.getEmployeeDomain() != null && scheduleDomain.getEmployeeDomain().getId() != null) {
            UserDomain employee = userGateway.findById(scheduleDomain.getEmployeeDomain().getId())
                    .orElseThrow(() -> new RelatedEntityNotFoundException(
                            "Funcionário com ID " + scheduleDomain.getEmployeeDomain().getId() + " não encontrado"));
            scheduleDomain.setEmployeeDomain(employee);
        }

        if (scheduleDomain.getPaymentTypeDomain() != null && scheduleDomain.getPaymentTypeDomain().getId() != null) {
            PaymentTypeDomain paymentType = paymentTypeGateway.findById(scheduleDomain.getPaymentTypeDomain().getId())
                    .orElseThrow(() -> new RelatedEntityNotFoundException(
                            "Tipo de pagamento com ID " + scheduleDomain.getPaymentTypeDomain().getId() + " não encontrado"));
            scheduleDomain.setPaymentTypeDomain(paymentType);
        }


        if (scheduleDomain.getItems() != null) {
            for (ScheduleItemDomain item : scheduleDomain.getItems()) {
                if (item.getService() != null && item.getService().getId() != null) {
                    ServiceDomain service = serviceGateway.findById(item.getService().getId())
                            .orElseThrow(() -> new RelatedEntityNotFoundException(
                                    "Serviço com ID " + item.getService().getId() + " não encontrado"));
                    item.setService(service);
                }
            }
        }

        if (scheduleGateway.existsByAppointmentDatetime(scheduleDomain.getAppointmentDatetime())) {
            throw new EntityConflictException("Horário já ocupado");
        }

        if(scheduleDomain.getAppointmentDatetime().isBefore(LocalDateTime.now())){
            throw new Exception("O Horário deve estar no futuro");
        }

        if (scheduleDomain.getItems() != null) {
            scheduleDomain.getItems().forEach(item -> item.setSchedule(scheduleDomain));
        }

        scheduleDomain.setId(null);
        scheduleDomain.setCreatedAt(LocalDateTime.now());
        scheduleDomain.setUpdatedAt(LocalDateTime.now());

        ScheduleDomain savedSchedule = scheduleGateway.save(scheduleDomain);

        calendarGateway.createEventForSalon(savedSchedule);
        calendarGateway.createEventForUser(savedSchedule);

        return savedSchedule;
    }
}