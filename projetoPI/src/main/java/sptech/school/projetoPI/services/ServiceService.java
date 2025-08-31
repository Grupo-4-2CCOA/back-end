package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.RelatedEntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceService extends AbstractService<sptech.school.projetoPI.core.domains.Service> {
    private final ServiceRepository repository;
    private final CategoryRepository categoryRepository;

    @Override
    public sptech.school.projetoPI.core.domains.Service postMethod(sptech.school.projetoPI.core.domains.Service service) {
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

    @Override
    public List<sptech.school.projetoPI.core.domains.Service> getAllMethod() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public sptech.school.projetoPI.core.domains.Service getByIdMethod(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O serviço com o ID %d não foi encontrado".formatted(id)
                )
        );
    }

    @Override
    public sptech.school.projetoPI.core.domains.Service putByIdMethod(sptech.school.projetoPI.core.domains.Service service, Integer id) {
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

    @Override
    public void deleteByIdMethod(Integer id) {
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

        sptech.school.projetoPI.core.domains.Service service = repository.findById(id).get();
        service.setActive(false);
        repository.save(service);
    }

    private void validateRequestBody(sptech.school.projetoPI.core.domains.Service service) {
        if (!categoryRepository.existsByIdAndActiveTrue(service.getCategory().getId())) {
            throw new RelatedEntityNotFoundException(
                    "A categoria com o ID %d não foi encontrada".formatted(service.getCategory().getId())
            );
        }
    }
}
