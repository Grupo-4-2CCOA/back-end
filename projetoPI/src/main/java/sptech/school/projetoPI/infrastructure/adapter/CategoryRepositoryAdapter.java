package sptech.school.projetoPI.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domain.CategoryDomain;
import sptech.school.projetoPI.core.gateway.CategoryGateway;
import sptech.school.projetoPI.infrastructure.mapper.CategoryMapper;
import sptech.school.projetoPI.infrastructure.persistence.entity.CategoryJpaEntity;
import sptech.school.projetoPI.infrastructure.persistence.repository.JpaCategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryGateway {

    private final JpaCategoryRepository repository;

    @Override
    public CategoryDomain save(CategoryDomain categoryDomain) {
        CategoryJpaEntity jpaEntity = CategoryMapper.toJpaEntity(categoryDomain);
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
    public Optional<CategoryDomain> findByIdAndActiveTrue(Integer id) {
        return repository.findByIdAndActiveTrue(id).map(CategoryMapper::toDomain);
    }

    @Override
    public List<CategoryDomain> findAllByActiveTrue() {
        return repository.findAllByActiveTrue().stream()
                .map(CategoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CategoryDomain> findById(Integer id) {
        return repository.findById(id).map(CategoryMapper::toDomain);
    }
}