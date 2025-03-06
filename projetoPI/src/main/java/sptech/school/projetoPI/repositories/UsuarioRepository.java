package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);
}
