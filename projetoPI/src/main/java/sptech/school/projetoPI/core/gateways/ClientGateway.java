package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.Client;
import java.util.List;
import java.util.Optional;

public interface ClientGateway {
    Client save(Client client);
    Optional<Client> findById(Integer id);
    List<Client> findAllByActiveTrue();
    Optional<Client> findByIdAndActiveTrue(Integer id);
    Optional<Client> findByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNotAndCpf(Integer id, String cpf);
    boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email);
    boolean existsByIdNotAndPhone(Integer id, String phone);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdAndActiveTrue(Integer id);
}