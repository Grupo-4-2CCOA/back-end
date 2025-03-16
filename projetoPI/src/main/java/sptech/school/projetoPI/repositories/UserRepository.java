package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByCpfOrEmailIgnoreCase(String cpf, String email);
}
