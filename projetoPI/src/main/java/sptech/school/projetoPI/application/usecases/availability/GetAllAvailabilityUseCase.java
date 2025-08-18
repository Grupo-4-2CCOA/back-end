package sptech.school.projetoPI.application.usecases.availability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;

import java.time.LocalTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAllAvailabilityUseCase {
    private final AvailabilityGateway availabilityGateway;

    public List<Availability> execute() {
        log.info("{} - [INFO] GET: Retornando lista de entidades", LocalTime.now());
        return availabilityGateway.findAll();
    }
}
