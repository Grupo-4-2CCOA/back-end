package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.entities.User;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Client, Integer> {
    boolean existsByCpf(String cpf);
    boolean existsByIdNotAndCpf(Integer id, String cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNotAndPhone(Integer id, String phone);
    boolean existsByRoleId(Integer id);
    boolean existsByRoleName(String role);
    boolean existsByIdAndActiveTrue(Integer id);
    boolean existsByIdAndActiveFalse(Integer id);
    List<Client> findAllByActiveTrue();
    Optional<Client> findByIdAndActiveTrue(Integer id);
    List<Client> findAllByRoleId(Integer id);
    Optional<Client> findByEmail(String email);
}
