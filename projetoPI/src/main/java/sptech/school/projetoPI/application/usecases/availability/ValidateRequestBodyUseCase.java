package sptech.school.projetoPI.application.usecases.availability;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.gateways.AvailabilityGateway;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.RelatedEntityNotFoundException;

import java.time.LocalTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateRequestBodyUseCase {

    private final AvailabilityGateway availabilityGateway;

    public void execute(Availability availability) {
        if(!availabilityGateway.existsById(availability.getEmployee().getId())) {
            log.error("{} - [ERROR 404] REQUEST BODY VALIDATION: Entidade de ID {} não encontrada. Abortar execução", LocalTime.now(), availability.getEmployee().getId());
            throw new RelatedEntityNotFoundException(
                    "Employee com o ID %d não foi encontrado".formatted(availability.getEmployee().getId())
            );
        }
    }
}
