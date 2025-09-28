package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.RoleJpaEntity;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleJpaEntity, Integer> {
  Boolean existsByName(String name);
  Optional<RoleJpaEntity> findByName(String name);
  @Transactional
  void deleteByName(String name);
}
