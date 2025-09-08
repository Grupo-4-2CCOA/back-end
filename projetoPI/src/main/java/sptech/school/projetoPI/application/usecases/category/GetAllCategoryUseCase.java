package sptech.school.projetoPI.application.usecases.category;

import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.core.gateways.CategoryGateway;

import java.time.LocalTime;
import java.util.List;

public class GetAllCategoryUseCase {

    private final CategoryGateway repository;

    public GetAllCategoryUseCase(CategoryGateway repository) {
        this.repository = repository;
    }

    public List<Category> execute() {
        return repository.findAllByActiveTrue();
    }
}
