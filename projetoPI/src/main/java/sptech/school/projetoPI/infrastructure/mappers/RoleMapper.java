package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.infrastructure.dto.role.RoleRequestDto;
import sptech.school.projetoPI.infrastructure.dto.role.RoleResponseDto;
import sptech.school.projetoPI.infrastructure.dto.role.RoleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.RoleJpaEntity;

public class RoleMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static Role toDomain(RoleRequestDto request) {
        if (request == null) return null;
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        return role;
    }

    /* ========= ID -> DOMAIN (para referências) ========= */
    public static Role toDomain(Integer id) {
        if (id == null) return null;
        Role role = new Role();
        role.setId(id);
        return role;
    }

    /* ========= JPA -> DOMAIN ========= */
    public static Role toDomain(RoleJpaEntity jpa) {
        if (jpa == null) {
            return null;
        }

        Role domain = new Role();
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
    public static RoleResponseDto toResponseDto(Role domain) { // Renomeado para 'domain' para consistência
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
    public static RoleResumeResponseDto toResumeResponseDto(Role domain) { // Renomeado para 'domain'
        if(domain == null) return null;

        return RoleResumeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    /* ========= DOMAIN -> JPA ========= */
    public static RoleJpaEntity toJpaEntity(Role domain) {
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