package sptech.school.projetoPI.core.application.usecase.category;

import sptech.school.projetoPI.core.domain.CategoryDomain;
import sptech.school.projetoPI.core.gateway.CategoryGateway;

import java.util.List;

public class GetAllCategoryUseCase {

    private final CategoryGateway repository;

    public GetAllCategoryUseCase(CategoryGateway repository) {
        this.repository = repository;
    }

    public List<CategoryDomain> execute() {
        return repository.findAllByActiveTrue();
    }
}
