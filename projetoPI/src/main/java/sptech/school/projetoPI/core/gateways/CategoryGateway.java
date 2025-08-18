package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryGateway {
    Category save(Category category);
    boolean existsByName(String name);
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<Category> findByIdAndActiveTrue(Integer id);
    List<Category> findAllByActiveTrue();
}
