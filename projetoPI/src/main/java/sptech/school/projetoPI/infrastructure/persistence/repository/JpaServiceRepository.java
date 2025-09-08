package sptech.school.projetoPI.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.infrastructure.persistence.entity.ServiceJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaServiceRepository extends JpaRepository<ServiceJpaEntity, Integer> {
    boolean existsByName(String name);
    boolean existsByCategoryId(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<ServiceJpaEntity> findByIdAndActiveTrue(Integer id);
    List<ServiceJpaEntity> findAllByCategoryId(Integer id);
    List<ServiceJpaEntity> findAllByActiveTrue();
    boolean existsById(Integer id);
    Optional<ServiceJpaEntity> findById(Integer id);
}
