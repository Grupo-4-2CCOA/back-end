package sptech.school.projetoPI.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.infrastructure.persistence.CategoryJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaCategoryRepository extends JpaRepository<CategoryJpaEntity, Integer>{
    Category save(Category category);
    boolean existsByName(String name);
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<Category> findByIdAndActiveTrue(Integer id);
    List<Category> findAllByActiveTrue();
}
