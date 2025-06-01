package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.exceptions.exceptionClass.*;
import sptech.school.projetoPI.repositories.CategoryRepository;
import sptech.school.projetoPI.repositories.ServiceRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService {
    private final ServiceRepository repository;
    private final CategoryRepository categoryRepository;

    public sptech.school.projetoPI.entities.Service signService(sptech.school.projetoPI.entities.Service service) {
        if (repository.existsByName(service.getName())) {
            throw new EntityConflictException(
                    "Este serviço já está cadastrado"
            );
        }

        validateRequestBody(service);
        service.setId(null);
        service.setCreatedAt(LocalDateTime.now());
        service.setUpdatedAt(LocalDateTime.now());
        return repository.save(service);
    }

    public List<sptech.school.projetoPI.entities.Service> getAllServices() {
        return repository.findAllByActiveTrue();
    }

    public sptech.school.projetoPI.entities.Service getServiceById(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O serviço com o ID %d não foi encontrado".formatted(id)
                )
        );
    }

    public sptech.school.projetoPI.entities.Service updateServiceById(sptech.school.projetoPI.entities.Service service, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O serviço com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O serviço com o ID %d já está inativo".formatted(id)
            );
        }

        if (repository.existsByIdNotAndName(id, service.getName())) {
            throw new EntityConflictException(
                    "Este serviço já está cadastrado"
            );
        }

        validateRequestBody(service);
        service.setId(id);
        service.setCreatedAt(repository.findById(id).get().getCreatedAt());
        service.setUpdatedAt(LocalDateTime.now());
        return repository.save(service);
    }

    public void deleteServiceById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O serviço com o ID %d não foi encontrado".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O serviço com o ID %d já está inativo".formatted(id)
            );
        }

        sptech.school.projetoPI.entities.Service service = repository.findById(id).get();
        service.setActive(false);
        repository.save(service);
    }

    // Validação do POST & PUT
    private void validateRequestBody(sptech.school.projetoPI.entities.Service service) {
        if (!categoryRepository.existsByIdAndActiveTrue(service.getCategory().getId())) {
            throw new RelatedEntityNotFoundException(
                    "A categoria com o ID %d não foi encontrada".formatted(service.getCategory().getId())
            );
        }
    }
}
