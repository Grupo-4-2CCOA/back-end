package sptech.school.projetoPI.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domain.CategoryDomain;
import sptech.school.projetoPI.infrastructure.persistence.entity.CategoryJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaCategoryRepository extends JpaRepository<CategoryJpaEntity, Integer>{
    CategoryDomain save(CategoryDomain categoryDomain);
    boolean existsByName(String name);
    boolean existsById(Integer id);
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<CategoryJpaEntity> findByIdAndActiveTrue(Integer id);
    List<CategoryJpaEntity> findAllByActiveTrue();
    Optional<CategoryJpaEntity> findById(Integer id);
}
