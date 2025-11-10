package sptech.school.projetoPI.core.application.usecases.schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

public class GetAllSchedulesByClient {
    private final ScheduleGateway scheduleGateway;

    public GetAllSchedulesByClient(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public Page<ScheduleDomain> execute(Pageable pageable, Integer id) {
        return scheduleGateway.findAllByClientId(id, pageable);
    }
}
