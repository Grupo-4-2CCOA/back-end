package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.infrastructure.persistence.entity.RoleJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<RoleJpaEntity, Integer> {
    Optional<RoleJpaEntity> findById(Integer id);
    boolean existsByName(String name);
    boolean existsById(Integer id);
    boolean existsByIdAndIsActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    Optional<RoleJpaEntity> findByIdAndIsActiveTrue(Integer id);
    List<RoleJpaEntity> findAllByIsActiveTrue();
}
