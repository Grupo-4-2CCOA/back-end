package sptech.school.projetoPI.application.usecases.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.gateways.ScheduleGateway;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetSchedulebyIdUseCase {

    private final ScheduleGateway scheduleGateway;

    public Schedule getByIdMethod(Integer id) {
        return scheduleGateway.findById(id);
        // TODO: VALIDAR O ID CASO ELE FOR NULO/INVÁLIDO
    }

}
