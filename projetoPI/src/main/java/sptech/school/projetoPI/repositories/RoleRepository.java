package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByName(String name);
    boolean existsByIdAndActiveFalse(Integer id);
    Optional<Role> findByIdAndActiveTrue(Integer id);
    List<Role> findAllByActiveTrue();
    boolean existsByIdNotAndName(Integer id, String name);
}
