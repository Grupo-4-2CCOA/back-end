package sptech.school.projetoPI.infrastructure.mapper;

import sptech.school.projetoPI.core.domain.RoleDomain;
import sptech.school.projetoPI.core.application.command.role.RoleRequestDto;
import sptech.school.projetoPI.core.application.command.role.RoleResponseDto;
import sptech.school.projetoPI.core.application.command.role.RoleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.RoleJpaEntity;

public class RoleMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static RoleDomain toDomain(RoleRequestDto request) {
        if (request == null) return null;
        RoleDomain roleDomain = new RoleDomain();
        roleDomain.setName(request.getName());
        roleDomain.setDescription(request.getDescription());
        return roleDomain;
    }

    /* ========= ID -> DOMAIN (para referências) ========= */
    public static RoleDomain toDomain(Integer id) {
        if (id == null) return null;
        RoleDomain roleDomain = new RoleDomain();
        roleDomain.setId(id);
        return roleDomain;
    }

    /* ========= JPA -> DOMAIN ========= */
    public static RoleDomain toDomain(RoleJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        RoleDomain domain = new RoleDomain();
        domain.setId(jpa.getId());
        domain.setName(jpa.getName());
        domain.setDescription(jpa.getDescription());
        domain.setActive(jpa.getActive());
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());

        return domain;
    }

    /* ========= DTO -> JPA ========= */
    // Este método é redundante com o 'toJpaEntity(Role domain)' e pode causar confusão.
    // É recomendado que o mapper da infraestrutura converta apenas entre Domínio e JPA.
    // O mapeamento de DTO para JPA deve ser feito através do domínio (DTO -> DOMAIN -> JPA).
    // Removi o método 'toEntity' para evitar essa violação.

    /* ========= DOMAIN -> DTO (completo) ========= */
    public static RoleResponseDto toResponseDto(RoleDomain domain) { // Renomeado para 'domain' para consistência
        if(domain == null) return null;

        return RoleResponseDto.builder()
                .id(domain.getId())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .name(domain.getName())
                .description(domain.getDescription())
                .build();
    }

    /* ========= DOMAIN -> DTO (resumido) ========= */
    public static RoleResumeResponseDto toResumeResponseDto(RoleDomain domain) { // Renomeado para 'domain'
        if(domain == null) return null;

        return RoleResumeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    /* ========= DOMAIN -> JPA ========= */
    public static RoleJpaEntity toJpaEntity(RoleDomain domain) {
        if (domain == null) return null;

        return RoleJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .active(domain.getActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }
}