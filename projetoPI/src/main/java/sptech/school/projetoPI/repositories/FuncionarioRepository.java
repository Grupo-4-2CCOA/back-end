package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);
}
