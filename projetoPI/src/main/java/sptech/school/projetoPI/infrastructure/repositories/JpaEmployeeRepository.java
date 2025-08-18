package sptech.school.projetoPI.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.infrastructure.persistence.EmployeeJpaEntity;

import java.util.List;
import java.util.Optional;

public interface JpaEmployeeRepository extends JpaRepository<EmployeeJpaEntity, Integer> {
    boolean existsByCpf(String cpf);
    boolean existsByIdNotAndCpf(Integer id, String cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNotAndPhone(Integer id, String phone);
    boolean existsByRoleId(Integer id);
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByRoleName(String roleName);
    boolean existsByIdNotAndRoleName(Integer id, String roleName);
    Optional<Employee> findByIdAndActiveTrue(Integer id);
    List<Employee> findAllByActiveTrue();
    List<Employee> findAllByRoleId(Integer id);
    Optional<Employee> findByEmail(String email);
}
