package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.CategoryDomain;

import java.util.List;
import java.util.Optional;

public interface CategoryGateway {
    CategoryDomain save(CategoryDomain categoryDomain);
    boolean existsByName(String name);
    boolean existsById(Integer id);
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<CategoryDomain> findByIdAndActiveTrue(Integer id);
    List<CategoryDomain> findAllByActiveTrue();
    Optional<CategoryDomain> findById(Integer id);
}
