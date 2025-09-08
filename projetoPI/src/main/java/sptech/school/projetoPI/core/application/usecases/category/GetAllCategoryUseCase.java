package sptech.school.projetoPI.core.application.usecases.category;

import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.gateways.CategoryGateway;

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
