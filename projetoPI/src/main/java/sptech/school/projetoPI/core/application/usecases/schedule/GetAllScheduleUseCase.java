package sptech.school.projetoPI.core.application.usecases.schedule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

import java.time.LocalDateTime;
import java.util.List;

public class GetAllScheduleUseCase {

    private final ScheduleGateway scheduleGateway;

    public GetAllScheduleUseCase(ScheduleGateway scheduleGateway) {
        this.scheduleGateway = scheduleGateway;
    }

    public Page<ScheduleDomain> execute(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate) {
        return scheduleGateway.findAll(pageable, startDate, endDate);
    }

}
