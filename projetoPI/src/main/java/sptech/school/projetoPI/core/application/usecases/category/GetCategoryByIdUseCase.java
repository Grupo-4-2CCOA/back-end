package sptech.school.projetoPI.core.application.usecases.category;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.gateways.CategoryGateway;

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
