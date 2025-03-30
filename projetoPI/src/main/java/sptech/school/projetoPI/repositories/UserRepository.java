package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByCpf(String cpf);
    boolean existsByIdNotAndCpf(Integer id, String cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNotAndPhone(Integer id, String phone);
}
