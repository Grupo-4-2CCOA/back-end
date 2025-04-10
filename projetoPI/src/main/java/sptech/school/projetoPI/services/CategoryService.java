package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Category;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.repositories.CategoryRepository;
import sptech.school.projetoPI.repositories.ServiceRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;
    private final ServiceRepository serviceRepository;

    public CategoryService(CategoryRepository repository, ServiceRepository serviceRepository) {
        this.repository = repository;
        this.serviceRepository = serviceRepository;
    }

    public Category signCategory(Category category) {
        if(repository.existsByName(category.getName())) {
            throw new EntityConflictException(
                    "Categoria com o mesmo nome já existe na base de dados"
            );
        }

        category.setId(null);
        return repository.save(category);
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Category getCategoryById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "A categoria com o ID %d não foi encontrada".formatted(id)
                )
        );
    }

    public Category updateCategoryById(Category category, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "A categoria com o ID %d não foi encontrada".formatted(id)
            );
        }

        category.setId(id);
        return repository.save(category);
    }

    public ResponseEntity<Void> deleteCategoryById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "A categoria com o ID %d não foi encontrada".formatted(id)
            );
        }

        if (serviceRepository.existsByCategoryId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes serviços estão relacionados com esta categoria: %s".formatted(serviceRepository.findAllByCategoryId(id)
                            .stream().map(sptech.school.projetoPI.entities.Service::getId).toList())
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
