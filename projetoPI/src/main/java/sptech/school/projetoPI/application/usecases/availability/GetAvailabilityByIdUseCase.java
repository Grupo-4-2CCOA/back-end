package sptech.school.projetoPI.application.usecases.availability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;

import java.time.LocalTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAvailabilityByIdUseCase {

    private final AvailabilityGateway availabilityGateway;

    public Availability execute(Integer id) {
        return availabilityGateway.findById(id)
                .map((entity) -> {
                    log.info("{} - [INFO] GET: Retornando entidade de ID {}", LocalTime.now(), id);
                    return entity;
                })
                .orElseThrow(() -> {
                    log.error("{} - [ERROR 404] GET: Entidade de ID {} não encontrada. Abortar execução", LocalTime.now(), id);
                    return new EntityNotFoundException(
                            "Availability de ID %d não foi encontrado".formatted(id)
                    );
                });
    }
}
