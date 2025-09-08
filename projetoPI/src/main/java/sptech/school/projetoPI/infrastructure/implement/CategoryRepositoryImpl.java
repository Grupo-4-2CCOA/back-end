package sptech.school.projetoPI.infrastructure.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.core.gateways.CategoryGateway;
import sptech.school.projetoPI.infrastructure.mappers.CategoryMapper;
import sptech.school.projetoPI.infrastructure.persistence.CategoryJpaEntity;
import sptech.school.projetoPI.infrastructure.repositories.JpaCategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryGateway {

    private final JpaCategoryRepository repository;

    @Override
    public Category save(Category category) {
        CategoryJpaEntity jpaEntity = CategoryMapper.toJpaEntity(category);
        CategoryJpaEntity savedEntity = repository.save(jpaEntity);
        return CategoryMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    @Override
    public boolean existsByIdAndActiveTrue(Integer id) {
        return repository.existsByIdAndActiveTrue(id);
    }

    @Override
    public boolean existsByIdAndActiveFalse(Integer id) {
        return repository.existsByIdAndActiveFalse(id);
    }

    @Override
    public boolean existsByIdNotAndName(Integer id, String name) {
        return repository.existsByIdNotAndName(id, name);
    }

    @Override
    public Optional<Category> findByIdAndActiveTrue(Integer id) {
        return repository.findByIdAndActiveTrue(id).map(CategoryMapper::toDomain);
    }

    @Override
    public List<Category> findAllByActiveTrue() {
        return repository.findAllByActiveTrue().stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return repository.findById(id).map(CategoryMapper::toDomain);
    }
}