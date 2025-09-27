package sptech.school.projetoPI.old.core.application.usecases.category;

import sptech.school.projetoPI.old.core.domains.CategoryDomain;
import sptech.school.projetoPI.old.core.gateways.CategoryGateway;

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
