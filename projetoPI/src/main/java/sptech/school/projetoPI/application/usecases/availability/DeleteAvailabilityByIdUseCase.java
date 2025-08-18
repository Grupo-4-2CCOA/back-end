package sptech.school.projetoPI.application.usecases.availability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;

import java.time.LocalTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteAvailabilityByIdUseCase {
    private final AvailabilityGateway availabilityGateway;

    public void execute(Integer id) {
        if (!availabilityGateway.existsById(id)) {
            log.error("{} - [ERROR 404] DELETE: Entidade de ID {} não encontrada. Abortar execução", LocalTime.now(), id);
            throw new EntityNotFoundException(
                    "Availability com o ID %d não foi encontrado".formatted(id)
            );
        }

        availabilityGateway.deleteById(id);
        log.info("{} - [INFO] DELETE: Entidade de ID {} inativada", LocalTime.now(), id);
    }
}
