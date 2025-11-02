package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.application.dto.user.UserRequestDto;
import sptech.school.projetoPI.core.application.dto.user.UserResponseDto;
import sptech.school.projetoPI.core.application.dto.user.UserResumeResponseDto;
import sptech.school.projetoPI.core.application.dto.login.UserTokenDto;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.infrastructure.persistence.entity.UserJpaEntity;


public class UserMapper {

    /* ========= TOKEN DTO ========= */
    public static UserTokenDto of(UserDomain usuario, String token) {
        UserTokenDto dto = new UserTokenDto();
        dto.setUserId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNome(usuario.getName());
        dto.setToken(token);
        dto.setTipoUsuario("CLIENTE");
        return dto;
    }

    /* ========= DTO -> DOMAIN ========= */
    public static UserDomain toDomain(UserRequestDto request) {
        if (request == null) return null;

        UserDomain userDomain = new UserDomain();
        userDomain.setName(request.getName());
        userDomain.setCpf(request.getCpf());
        userDomain.setEmail(request.getEmail());
        userDomain.setCep(request.getCep());
        userDomain.setPhone(request.getPhone());
        userDomain.setRoleDomain(RoleMapper.toDomain(request.getRole()));
        return userDomain;
    }

    public static UserDomain toDomain(Integer id) {
        if (id == null) return null;
        UserDomain domain = new UserDomain();
        domain.setId(id);
        return domain;
    }

    /* ========= DOMAIN -> DTO ========= */
    public static UserResponseDto toResponseDto(UserDomain domain) {
        if (domain == null) return null;
        return UserResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .cpf(domain.getCpf())
                .email(domain.getEmail())
                .cep(domain.getCep())
                .phone(domain.getPhone())
                .build();
    }

    public static UserResumeResponseDto toResumeResponseDto(UserDomain domain) {
        if (domain == null) return null;
        return UserResumeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .build();
    }

    /* ========= DOMAIN -> JPA (completo) ========= */
    public static UserJpaEntity toJpaEntity(UserDomain domain) {
        if (domain == null) return null;

        return UserJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .cpf(domain.getCpf())
                .email(domain.getEmail())
                .cep(domain.getCep())
                .phone(domain.getPhone())
                .isActive(domain.getActive() != null ? domain.getActive() : true)
                .role(RoleMapper.toJpaEntity(domain.getRoleDomain()))
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    /* ========= DOMAIN -> JPA (simples, sem relacionamentos) ========= */
    public static UserJpaEntity toJpaEntitySimple(UserDomain domain) {
        if (domain == null) return null;
        return UserJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .build();
    }

    /* ========= JPA -> DOMAIN ========= */
    public static UserDomain toDomain(UserJpaEntity jpa) {
        if (jpa == null) return null;

        UserDomain domain = new UserDomain();
        domain.setId(jpa.getId());
        domain.setName(jpa.getName());
        domain.setCpf(jpa.getCpf());
        domain.setEmail(jpa.getEmail());
        domain.setCep(jpa.getCep());
        domain.setPhone(jpa.getPhone());
        domain.setActive(jpa.getIsActive());
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());
        domain.setRoleDomain(RoleMapper.toDomain(jpa.getRole()));
        return domain;
    }
}
