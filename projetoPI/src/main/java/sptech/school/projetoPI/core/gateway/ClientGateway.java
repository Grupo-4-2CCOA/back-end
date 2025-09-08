package sptech.school.projetoPI.core.gateway;

import sptech.school.projetoPI.core.domain.ClientDomain;

import java.util.List;
import java.util.Optional;

public interface ClientGateway {
    ClientDomain save(ClientDomain clientDomain);
    boolean existsById(Integer id);
    List<ClientDomain> findAll();
    Optional<ClientDomain> findById(Integer id);
    ClientDomain deleteById(Integer id);
    boolean existsByCpf(String cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNotAndCpf(Integer id, String cpf);
    boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email);
    boolean existsByIdNotAndPhone(Integer id, String phone);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdAndActiveTrue(Integer id);
    List<ClientDomain> findAllByActiveTrue();
    Optional<ClientDomain> findByIdAndActiveTrue(Integer id);
    Optional<ClientDomain> findByEmail(String email);
}
