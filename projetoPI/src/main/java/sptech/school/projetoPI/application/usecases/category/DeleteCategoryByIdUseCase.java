package sptech.school.projetoPI.application.usecases.category;

import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.CategoryGateway;
import sptech.school.projetoPI.core.gateways.ServiceGateway;

import java.time.LocalDateTime;

public class DeleteCategoryByIdUseCase {

    private final CategoryGateway repository;
    private final ServiceGateway serviceRepository;

    public DeleteCategoryByIdUseCase(CategoryGateway repository, ServiceGateway serviceRepository) {
        this.repository = repository;
        this.serviceRepository = serviceRepository;
    }

    public void execute(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "Category com o ID %d não foi encontrada".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "Category com o ID %d já está inativa".formatted(id)
            );
        }

        if (serviceRepository.existsByCategoryId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes serviços estão relacionados com esta categoria: %s".formatted(serviceRepository.findAllByCategoryId(id)
                            .stream().map(ServiceDomain::getId).toList())
            );
        }

        Category category = repository.findById(id).get();
        category.setActive(false);
        category.setUpdatedAt(LocalDateTime.now());
        repository.save(category);
    }
}
