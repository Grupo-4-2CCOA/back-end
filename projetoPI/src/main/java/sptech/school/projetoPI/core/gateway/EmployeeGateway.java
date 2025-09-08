package sptech.school.projetoPI.core.gateway;

import sptech.school.projetoPI.core.domain.EmployeeDomain;

import java.util.List;
import java.util.Optional;

public interface EmployeeGateway {
    EmployeeDomain save(EmployeeDomain employeeDomain);
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
    boolean existsById(Integer id);
    Optional<EmployeeDomain> findByIdAndActiveTrue(Integer id);
    List<EmployeeDomain> findAllByActiveTrue();
    Optional<EmployeeDomain> findById(Integer id);
    List<EmployeeDomain> findAllByRoleId(Integer id);
    Optional<EmployeeDomain> findByEmail(String email);
}
