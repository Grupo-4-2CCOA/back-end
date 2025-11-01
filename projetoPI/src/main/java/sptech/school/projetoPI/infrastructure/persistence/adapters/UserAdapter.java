package sptech.school.projetoPI.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.UserGateway;
import sptech.school.projetoPI.infrastructure.mappers.UserMapper;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAdapter implements UserGateway {

    private final JpaUserRepository jpaRepository;

    @Override
    public UserDomain save(UserDomain clientDomain) {
        var entity = UserMapper.toJpaEntity(clientDomain);
        var saved = jpaRepository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public boolean existsById(Integer id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public List<UserDomain> findAll() {
        return jpaRepository.findAll().stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDomain> findById(Integer id) {
        return jpaRepository.findById(id).map(UserMapper::toDomain);
    }

    @Override
    public UserDomain deleteById(Integer id) {
        Optional<UserDomain> clientOpt = findById(id);
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
        return jpaRepository.existsByIdAndIsActiveFalse(id);
    }

    @Override
    public boolean existsByIdAndActiveTrue(Integer id) {
        return jpaRepository.existsByIdAndIsActiveTrue(id);
    }

    @Override
    public List<UserDomain> findAllByActiveTrue() {
        return jpaRepository.findAllByIsActiveTrue().stream().map(UserMapper::toDomain).toList();
    }

    @Override
    public Optional<UserDomain> findByIdAndActiveTrue(Integer id) {
        return jpaRepository.findByIdAndIsActiveTrue(id).map(UserMapper::toDomain);
    }

    @Override
    public Optional<UserDomain> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public boolean existsByIdNotAndRoleName(Integer id, String roleName) {
        return jpaRepository.existsByIdNotAndRoleName(id, roleName);
    }
}
