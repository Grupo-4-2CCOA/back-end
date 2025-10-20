package sptech.school.projetoPI.infrastructure.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sptech.school.projetoPI.infrastructure.persistence.entity.UserJpaEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserRepository extends JpaRepository<UserJpaEntity, Integer> {
    // Métodos de consulta personalizados do Spring Data JPA
    boolean existsByCpf(String cpf);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhone(String phone);
    boolean existsByIdNotAndCpf(Integer id, String cpf);
    boolean existsByIdNotAndEmailIgnoreCase(Integer id, String email);
    boolean existsByIdNotAndPhone(Integer id, String phone);
    boolean existsByIdAndIsActiveFalse(Integer id);
    boolean existsByIdAndIsActiveTrue(Integer id);
    List<UserJpaEntity> findAllByIsActiveTrue();
    Optional<UserJpaEntity> findByIdAndIsActiveTrue(Integer id);
    Optional<UserJpaEntity> findByEmail(String email);
    boolean existsByRoleId(Integer id);
    boolean existsByRoleName(String roleName);
    boolean existsByIdNotAndRoleName(Integer id, String roleName);
    List<UserJpaEntity> findAllByRoleId(Integer id);
}