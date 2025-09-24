package sptech.school.projetoPI.refactor.infraestructure.mapper;

import sptech.school.projetoPI.refactor.core.application.command.user.CreateUserCommand;
import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;
import sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity.UserEntity;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.user.CreateUserResponseDto;

public class UserMapper {

    // Command -> Domain
    public static UserDomain toDomain(CreateUserCommand command) {
        return new UserDomain(
                command.id(),
                command.isActive(),
                command.createdAt(),
                command.updatedAt(),
                command.name(),
                command.email(),
                command.cpf(),
                command.phone(),
                command.cep(),
                command.roleDomain()
        );
    }

    // Domain -> Entity
    public static UserEntity toEntity(UserDomain domain) {
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setActive(domain.getActive());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());
        entity.setName(domain.getName());
        entity.setEmail(domain.getEmail());
        entity.setCpf(domain.getCpf());
        entity.setPhone(domain.getPhone());
        entity.setCep(domain.getCep());
        entity.setRoleEntity(domain.getRoleDomain());
        return entity;
    }

    // Entity -> Domain
    public static UserDomain toDomain(UserEntity entity) {
        return new UserDomain(
                entity.getId(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getName(),
                entity.getEmail(),
                entity.getCpf(),
                entity.getPhone(),
                entity.getCep(),
                entity.getRoleEntity()
        );
    }

    // Domain -> DTO
    public static CreateUserResponseDto toDto(UserDomain domain) {
        return new CreateUserResponseDto(
                domain.getName(),
                domain.getCpf(),
                domain.getEmail(),
                domain.getPhone(),
                domain.getCep(),
                domain.getRoleDomain()
        );
    }

}