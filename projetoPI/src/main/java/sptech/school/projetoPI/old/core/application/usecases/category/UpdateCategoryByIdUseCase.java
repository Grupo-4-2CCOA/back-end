package sptech.school.projetoPI.old.core.application.usecases.category;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.old.core.domains.CategoryDomain;
import sptech.school.projetoPI.old.core.gateways.CategoryGateway;

import java.time.LocalDateTime;

public class UpdateCategoryByIdUseCase {

    private final CategoryGateway repository;

    public UpdateCategoryByIdUseCase(CategoryGateway repository) {
        this.repository = repository;
    }

    public CategoryDomain execute(CategoryDomain categoryDomain, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Category com o ID %d não foi encontrada".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "Category com o ID %d já está inativa".formatted(id)
            );
        }

        if(repository.existsByIdNotAndName(id, categoryDomain.getName())) {
            throw new EntityConflictException(
                    "Category com o mesmo nome já existe na base de dados"
            );
        }

        categoryDomain.setId(id);
        categoryDomain.setCreatedAt(repository.findById(id).get().getCreatedAt());
        categoryDomain.setUpdatedAt(LocalDateTime.now());
        return repository.save(categoryDomain);
    }
}
