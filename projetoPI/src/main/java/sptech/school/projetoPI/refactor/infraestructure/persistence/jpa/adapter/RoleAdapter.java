package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.gateway.RoleGateway;
import sptech.school.projetoPI.refactor.infraestructure.mapper.RoleMapper;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.RoleJpaEntity;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.repository.RoleRepository;

import java.util.Optional;

// TODO: adicionar lógica de soft-delete (com verificação de isActive nos exists) nos UseCases;
@Service
@RequiredArgsConstructor
public class RoleAdapter implements RoleGateway {
  private final RoleRepository roleRepository;

  @Override
  public Boolean existsById(Integer id) {
    return roleRepository.existsById(id);
  }

  @Override
  public Optional<RoleDomain> findById(Integer id) {
    RoleJpaEntity roleJpaEntity = roleRepository.findById(id).orElse(null);
    RoleDomain roleDomain = RoleMapper.toRoleDomain(roleJpaEntity);
    return Optional.ofNullable(roleDomain);
  }

  @Override
  public void save(RoleDomain roleDomain) {
    RoleJpaEntity roleJpaEntity = RoleMapper.toRoleJpaEntity(roleDomain);
    roleRepository.save(roleJpaEntity);
  }

  @Override
  public void updateById(Integer id, RoleDomain roleDomain) {
    RoleJpaEntity roleJpaEntity = RoleMapper.toRoleJpaEntity(roleDomain);
    roleJpaEntity.setId(id);
    roleRepository.save(roleJpaEntity);
  }

  @Override
  public void deleteById(Integer id) {
    roleRepository.deleteById(id);
  }

  @Override
  public Boolean existsByName(String name) {
    return roleRepository.existsByName(name);
  }

  @Override
  public Optional<RoleDomain> findByName(String name) {
    RoleJpaEntity roleJpaEntity = roleRepository.findByName(name).orElse(null);
    RoleDomain roleDomain = RoleMapper.toRoleDomain(roleJpaEntity);
    return Optional.ofNullable(roleDomain);
  }

  @Override
  public void deleteByName(String name) {
    roleRepository.deleteByName(name);
  }
}
