package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.exceptions.EntityConflictException;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.ForeignKeyConstraintException;
import sptech.school.projetoPI.repositories.ScheduleRepository;
import sptech.school.projetoPI.repositories.ServiceRepository;

import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository repository;
    private final ScheduleRepository scheduleRepository;

    public ServiceService(ServiceRepository repository, ScheduleRepository scheduleRepository) {
        this.repository = repository;
        this.scheduleRepository = scheduleRepository;
    }

    public sptech.school.projetoPI.entities.Service signService(sptech.school.projetoPI.entities.Service service) {
        validateRequestBody(service);
        service.setId(null);
        return repository.save(service);
    }

    public List<sptech.school.projetoPI.entities.Service> getAllServices() {
        return repository.findAll();
    }

    public sptech.school.projetoPI.entities.Service getServiceById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O serviço com o id %d não foi encontrado".formatted(id)
                )
        );
    }

    public sptech.school.projetoPI.entities.Service updateServiceById(sptech.school.projetoPI.entities.Service service, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O serviço com o ID %d não foi encontrado".formatted(id)
            );
        }

        validateRequestBody(service);
        service.setId(id);
        return repository.save(service);
    }

    public ResponseEntity<Void> deleteServiceById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O serviço com o id %d não foi encontrado".formatted(id)
            );
        }

        if(scheduleRepository.existsByServiceId(id)) {
            throw new ForeignKeyConstraintException(
                    "O serviço de ID %d não pode ser apagado porque está relacionado com um ou vários agendamentos".formatted(id)
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

    // Validação do POST & PUT
    public void validateRequestBody(sptech.school.projetoPI.entities.Service service) {
        if (repository.existsByDescriptionIgnoreCase(service.getDescription())) {
            throw new EntityConflictException(
                    "Este serviço já está cadastrado"
            );
        }
    }
}
