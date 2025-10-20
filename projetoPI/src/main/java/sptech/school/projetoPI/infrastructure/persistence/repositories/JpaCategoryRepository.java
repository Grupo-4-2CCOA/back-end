package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.infrastructure.persistence.entity.CategoryJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaCategoryRepository extends JpaRepository<CategoryJpaEntity, Integer>{
    CategoryDomain save(CategoryDomain categoryDomain);
    boolean existsByName(String name);
    boolean existsById(Integer id);
    boolean existsByIdAndIsActiveTrue(Integer id);
    boolean existsByIdAndIsActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<CategoryJpaEntity> findByIdAndIsActiveTrue(Integer id);
    List<CategoryJpaEntity> findAllByIsActiveTrue();
    Optional<CategoryJpaEntity> findById(Integer id);
}
