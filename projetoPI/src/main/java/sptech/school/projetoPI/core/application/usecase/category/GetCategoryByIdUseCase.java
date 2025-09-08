package sptech.school.projetoPI.core.application.usecase.category;

import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domain.CategoryDomain;
import sptech.school.projetoPI.core.gateway.CategoryGateway;

public class GetCategoryByIdUseCase {

    private final CategoryGateway repository;

    public GetCategoryByIdUseCase(CategoryGateway repository) {
        this.repository = repository;
    }

    public CategoryDomain execute(Integer id) {
        return repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException(
                            "Category de ID %d não foi encontrado".formatted(id)
                    ));
    }
}
