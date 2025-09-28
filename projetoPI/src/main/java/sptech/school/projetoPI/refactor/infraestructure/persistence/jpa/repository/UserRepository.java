package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.UserJpaEntity;

public interface UserRepository extends JpaRepository<UserJpaEntity, Integer> {
  Boolean existsByRoleJpaEntity_Name(String name);
}
