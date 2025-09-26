package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.RoleJpaEntity;

public interface RoleRepository extends JpaRepository<RoleJpaEntity, Integer> {
  Boolean existsByName(String name);
}
