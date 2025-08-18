package sptech.school.projetoPI.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.infrastructure.persistence.RoleJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaRoleRepository extends JpaRepository<RoleJpaEntity, Integer> {
    Role save(Role client);
    boolean existsByName(String name);
    boolean existsById(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdNotAndName(Integer id, String name);
    boolean existsByRoleId(Integer id);
    Optional<Role> findByIdAndActiveTrue(Integer id);
    Optional<RoleJpaEntity> findById(Integer id);
    Optional<Role> findAllByRoleId(Integer id);
    List<Role> findAllByActiveTrue();
}
