package sptech.school.projetoPI.old.core.application.usecases.schedule;

import sptech.school.projetoPI.old.core.domains.ScheduleItemDomain;
import sptech.school.projetoPI.old.core.domains.ServiceDomain;
import sptech.school.projetoPI.old.core.gateways.ScheduleGateway;
import sptech.school.projetoPI.old.core.gateways.ScheduleItemGateway;
import sptech.school.projetoPI.old.core.gateways.ServiceGateway;

import java.util.ArrayList;
import java.util.List;

public class GetServiceNamesByScheduleIdUseCase {

    private final ScheduleGateway scheduleGateway;
    private final ScheduleItemGateway scheduleItemGateway;
    private final ServiceGateway serviceGateway;

    public GetServiceNamesByScheduleIdUseCase(ScheduleGateway scheduleGateway, ScheduleItemGateway scheduleItemGateway, ServiceGateway serviceGateway) {
        this.scheduleGateway = scheduleGateway;
        this.scheduleItemGateway = scheduleItemGateway;
        this.serviceGateway = serviceGateway;
    }

    public List<String> execute(Integer id) {
        List<ScheduleItemDomain> scheduleItemDomains = scheduleItemGateway.findAllBySchedule_Id(id);
        List<ServiceDomain> serviceDomains = scheduleItemDomains.stream().map(scheduleItemDomain -> serviceGateway.findById(scheduleItemDomain.getId()).get()).toList();

        if(serviceDomains.isEmpty()){
            return new ArrayList<>();
        }

        return serviceDomains.stream().map(serviceDomain -> serviceDomain.getName()).toList();
    }
}
