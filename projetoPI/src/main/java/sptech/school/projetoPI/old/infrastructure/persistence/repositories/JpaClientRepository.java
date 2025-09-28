package sptech.school.projetoPI.old.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.projetoPI.old.infrastructure.persistence.entity.ClientJpaEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaClientRepository extends JpaRepository<ClientJpaEntity, Integer> {
    // Métodos de consulta personalizados do Spring Data JPA
    boolean existsByCpf(String cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNotAndCpf(Integer id, String cpf);
    boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email);
    boolean existsByIdNotAndPhone(Integer id, String phone);
    boolean existsByIdAndActiveFalse(Integer id);
    boolean existsByIdAndActiveTrue(Integer id);
    List<ClientJpaEntity> findAllByActiveTrue();
    Optional<ClientJpaEntity> findByIdAndActiveTrue(Integer id);
    Optional<ClientJpaEntity> findByEmail(String email);
}