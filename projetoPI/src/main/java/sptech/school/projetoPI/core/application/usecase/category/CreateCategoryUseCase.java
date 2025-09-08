package sptech.school.projetoPI.core.application.usecase.category;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.core.domain.CategoryDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateway.CategoryGateway;

import java.time.LocalDateTime;

public class CreateCategoryUseCase {

    private final CategoryGateway repository;

    public CreateCategoryUseCase(CategoryGateway repository) {
        this.repository = repository;
    }

    public CategoryDomain execute(CategoryDomain categoryDomain) {
        if(repository.existsByName(categoryDomain.getName())) {
            throw new EntityConflictException(
                    Logs.POST_NAME_CONFLICT.getMessage().formatted(categoryDomain.getName())
            );
        }

        categoryDomain.setId(null);
        categoryDomain.setCreatedAt(LocalDateTime.now());
        categoryDomain.setUpdatedAt(LocalDateTime.now());
        return repository.save(categoryDomain);
    }
}
