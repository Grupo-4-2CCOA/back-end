package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.enums.EmployeeRole;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
//    boolean existsByCpfOrEmailIgnoreCaseOrPhone(String cpf, String email, String phone);
//    boolean existsByIdNotAndCpfOrIdNotAndEmailIgnoreCaseOrIdNotAndPhone(Integer id1, String cpf, Integer id2, String email, Integer id3, String phone);
    boolean existsByCpf(String cpf);
    boolean existsByIdNotAndCpf(Integer id, String cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNotAndPhone(Integer id, String phone);
    boolean existsByRole(EmployeeRole role);
    boolean existsByIdNotAndRole(Integer id, EmployeeRole role);
}
