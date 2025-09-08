package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.core.application.usecase.category.*;
import sptech.school.projetoPI.core.gateway.CategoryGateway;
import sptech.school.projetoPI.core.gateway.ServiceGateway;

@Configuration
public class CategoryConfig {

    @Bean
    public CreateCategoryUseCase createCategoryUseCase(CategoryGateway categoryGateway) {
        return new CreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetAllCategoryUseCase getAllCategoriesUseCase(CategoryGateway categoryGateway) {
        return new GetAllCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryByIdUseCase getCategoryByIdUseCase(CategoryGateway categoryGateway) {
        return new GetCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryByIdUseCase updateCategoryByIdUseCase(CategoryGateway categoryGateway) {
        return new UpdateCategoryByIdUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryByIdUseCase deleteCategoryByIdUseCase(CategoryGateway categoryGateway, ServiceGateway serviceGateway) {
        return new DeleteCategoryByIdUseCase(categoryGateway, serviceGateway);
    }
}