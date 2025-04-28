package sptech.school.projetoPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.projetoPI.entities.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    boolean existsByCpf(String cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNotAndCpf(Integer id, String cpf);
    boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email);
    boolean existsByIdNotAndPhone(Integer id, String phone);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdAndActiveTrue(Integer id);
    List<Client> findAllByActiveTrue();
    Optional<Client> findByIdAndActiveTrue(Integer id);
    Optional<Client> findByEmail(String email);
}
