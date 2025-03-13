package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);

}
