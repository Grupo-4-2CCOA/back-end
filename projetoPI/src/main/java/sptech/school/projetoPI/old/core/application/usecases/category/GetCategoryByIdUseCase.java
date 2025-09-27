package sptech.school.projetoPI.old.core.application.usecases.category;

import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.old.core.domains.CategoryDomain;
import sptech.school.projetoPI.old.core.gateways.CategoryGateway;

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
