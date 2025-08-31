package sptech.school.projetoPI.application.usecases.category;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.core.gateways.CategoryGateway;

import java.time.LocalTime;

public class GetCategoryByIdUseCase {

    private final CategoryGateway repository;

    public GetCategoryByIdUseCase(CategoryGateway repository) {
        this.repository = repository;
    }

    public Category execute(Integer id) {
        return repository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException(
                            "Category de ID %d não foi encontrado".formatted(id)
                    ));
    }
}
