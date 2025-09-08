package sptech.school.projetoPI.application.usecases.category;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.CategoryGateway;

import java.time.LocalDateTime;

public class CreateCategoryUseCase {

    private final CategoryGateway repository;

    public CreateCategoryUseCase(CategoryGateway repository) {
        this.repository = repository;
    }

    public Category execute(Category category) {
        if(repository.existsByName(category.getName())) {
            throw new EntityConflictException(
                    Logs.POST_NAME_CONFLICT.getMessage().formatted(category.getName())
            );
        }

        category.setId(null);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return repository.save(category);
    }
}
