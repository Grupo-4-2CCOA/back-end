package sptech.school.projetoPI.infrastructure.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.infrastructure.mappers.ClientMapper;
import sptech.school.projetoPI.infrastructure.persistence.ClientJpaEntity;
import sptech.school.projetoPI.infrastructure.repositories.JpaClientRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientPersistenceService implements ClientGateway {

    private final JpaClientRepository jpaRepository;

    @Override
    public Client save(Client client) {
        ClientJpaEntity jpaEntity = ClientMapper.toJpaEntity(client);
        ClientJpaEntity savedJpaEntity = jpaRepository.save(jpaEntity);
        return ClientMapper.toDomain(savedJpaEntity);
    }

    @Override
    public Optional<Client> findById(Integer id) {
        return jpaRepository.findById(id).map(ClientMapper::toDomain);
    }

    @Override
    public Optional<Client> findByIdAndActiveTrue(Integer id) {
        return jpaRepository.findByIdAndActiveTrue(id).map(ClientMapper::toDomain);
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(ClientMapper::toDomain);
    }

    @Override
    public List<Client> findAllByActiveTrue() {
        return jpaRepository.findAllByActiveTrue().stream()
                .map(ClientMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return jpaRepository.existsByCpf(cpf);
    }

    // Implemente todos os outros métodos de validação aqui, chamando o jpaRepository
    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        return jpaRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return false;
    }

    @Override
    public boolean existsByIdNotAndCpf(Integer id, String cpf) {
        return false;
    }

    @Override
    public boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email) {
        return false;
    }

    @Override
    public boolean existsByIdNotAndPhone(Integer id, String phone) {
        return false;
    }

    @Override
    public boolean existsByIdAndActiveFalse(Integer id) {
        return false;
    }

    @Override
    public boolean existsByIdAndActiveTrue(Integer id) {
        return false;
    }
}