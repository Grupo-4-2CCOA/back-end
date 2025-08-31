package sptech.school.projetoPI.application.usecases.category;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.core.gateways.CategoryGateway;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class UpdateCategoryByIdUseCase {

    private final CategoryGateway repository;

    public UpdateCategoryByIdUseCase(CategoryGateway repository) {
        this.repository = repository;
    }

    public Category execute(Category category, Integer id) {
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

        if(repository.existsByIdNotAndName(id, category.getName())) {
            throw new EntityConflictException(
                    "Category com o mesmo nome já existe na base de dados"
            );
        }

        category.setId(id);
        category.setCreatedAt(repository.findById(id).get().getCreatedAt());
        category.setUpdatedAt(LocalDateTime.now());
        return repository.save(category);
    }
}
