package sptech.school.projetoPI.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domains.Service;
import sptech.school.projetoPI.infrastructure.persistence.ServiceJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaServiceRepository extends JpaRepository<ServiceJpaEntity, Integer> {
    boolean existsByName(String name);
    boolean existsByCategoryId(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<Service> findByIdAndActiveTrue(Integer id);
    List<Service> findAllByCategoryId(Integer id);
    List<Service> findAllByActiveTrue();
}
