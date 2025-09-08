package sptech.school.projetoPI.infrastructure.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.infrastructure.mappers.ClientMapper;
import sptech.school.projetoPI.infrastructure.repositories.JpaClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientGateway {

    private final JpaClientRepository jpaRepository;

    @Override
    public Client save(Client client) {
        var entity = ClientMapper.toJpaEntity(client);
        var saved = jpaRepository.save(entity);
        return ClientMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(Integer id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<Client> findAll() {
        return jpaRepository.findAll().stream()
                .map(ClientMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return jpaRepository.findById(id).map(ClientMapper::toDomain);
    }

    @Override
    public Client deleteById(Integer id) {
        Optional<Client> clientOpt = findById(id);
        clientOpt.ifPresent(a -> jpaRepository.deleteById(id));
        return clientOpt.orElse(null);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }

    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return jpaRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return jpaRepository.existsByPhone(phone);
    }

    @Override
    public boolean existsByIdNotAndCpf(Integer id, String cpf) {
        return jpaRepository.existsByIdNotAndCpf(id, cpf);
    }

    @Override
    public boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email) {
        return jpaRepository.existsByIdNotAndEmailIgnoreCase(id, email);
    }

    @Override
    public boolean existsByIdNotAndPhone(Integer id, String phone) {
        return jpaRepository.existsByIdNotAndPhone(id, phone);
    }

    @Override
    public boolean existsByIdAndActiveFalse(Integer id) {
        return jpaRepository.existsByIdAndActiveFalse(id);
    }

    @Override
    public boolean existsByIdAndActiveTrue(Integer id) {
        return jpaRepository.existsByIdAndActiveTrue(id);
    }

    @Override
    public List<Client> findAllByActiveTrue() {
        return jpaRepository.findAllByActiveTrue().stream().map(ClientMapper::toDomain).toList();
    }

    @Override
    public Optional<Client> findByIdAndActiveTrue(Integer id) {
        return jpaRepository.findByIdAndActiveTrue(id).map(ClientMapper::toDomain);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(ClientMapper::toDomain);
    }
}
