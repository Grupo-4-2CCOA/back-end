package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Category;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.CategoryRepository;
import sptech.school.projetoPI.repositories.ServiceRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService extends AbstractService<Category> {
    private final CategoryRepository repository;
    private final ServiceRepository serviceRepository;

    @Override
    public Category postMethod(Category category) {
        if(repository.existsByName(category.getName())) {
            throw new EntityConflictException(
                    "Categoria com o mesmo nome já existe na base de dados"
            );
        }

        category.setId(null);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return repository.save(category);
    }

    @Override
    public List<Category> getAllMethod() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public Category getByIdMethod(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "A categoria com o ID %d não foi encontrada".formatted(id)
                )
        );
    }

    @Override
    public Category putByIdMethod(Category category, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "A categoria com o ID %d não foi encontrada".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "A categoria com o ID %d já está inativa".formatted(id)
            );
        }

        if(repository.existsByIdNotAndName(id, category.getName())) {
            throw new EntityConflictException(
                    "Categoria com o mesmo nome já existe na base de dados"
            );
        }

        category.setId(id);
        category.setCreatedAt(repository.findById(id).get().getCreatedAt());
        category.setUpdatedAt(LocalDateTime.now());
        return repository.save(category);
    }

    @Override
    public void deleteByIdMethod(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "A categoria com o ID %d não foi encontrada".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "A categoria com o ID %d já está inativa".formatted(id)
            );
        }

        if (serviceRepository.existsByCategoryId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes serviços estão relacionados com esta categoria: %s".formatted(serviceRepository.findAllByCategoryId(id)
                            .stream().map(sptech.school.projetoPI.entities.Service::getId).toList())
            );
        }

        Category category = repository.findById(id).get();
        category.setActive(false);
        category.setUpdatedAt(LocalDateTime.now());
        repository.save(category);
    }
}
