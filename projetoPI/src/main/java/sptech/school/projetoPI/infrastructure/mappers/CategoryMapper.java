package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.CategoryDomain;
import sptech.school.projetoPI.core.application.dto.category.CategoryRequestDto;
import sptech.school.projetoPI.core.application.dto.category.CategoryResponseDto;
import sptech.school.projetoPI.core.application.dto.category.CategoryResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.CategoryJpaEntity;

public class CategoryMapper {

    /* ========= DTO -> DOMAIN (Controller to Use Case) ========= */
    public static CategoryDomain toDomain(CategoryRequestDto requestObject) {
        if (requestObject == null) return null;

        CategoryDomain categoryDomain = new CategoryDomain();
        categoryDomain.setName(requestObject.getName());
        categoryDomain.setDescription(requestObject.getDescription());
        categoryDomain.setActive(true);
        return categoryDomain;
    }

    public static CategoryDomain toDomain(Integer id) {
        if (id == null) return null;

        CategoryDomain categoryDomain = new CategoryDomain();
        categoryDomain.setId(id);
        return categoryDomain;
    }

    /* ========= DOMAIN -> DTO (Full Response) ========= */
    public static CategoryResponseDto toResponseDto(CategoryDomain domain) {
        if (domain == null) return null;

        return CategoryResponseDto.builder()
                .id(domain.getId())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .name(domain.getName())
                .description(domain.getDescription())
                .build();
    }

    /* ========= DOMAIN -> DTO (Resume Response) ========= */
    public static CategoryResumeResponseDto toResumeResponseDto(CategoryDomain domain) {
        if (domain == null) return null;

        return CategoryResumeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    //---------------------------------------------------------
    // Mapeamento bidirecional para a camada de persistência (JPA)

    /* ========= DOMAIN -> JPA (Use Case to Repository) ========= */
    public static CategoryJpaEntity toJpaEntity(CategoryDomain domain) {
        if (domain == null) return null;

        // O tipo de retorno foi corrigido para CategoryJpaEntity
        return CategoryJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .active(domain.getActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public static CategoryJpaEntity toJpaEntity(Integer id) {
        if (id == null) return null;

        CategoryJpaEntity entity = new CategoryJpaEntity();
        entity.setId(id);
        return entity;
    }

    /* ========= JPA -> DOMAIN (Repository to Use Case) ========= */
    public static CategoryDomain toDomain(CategoryJpaEntity jpa) {
        if (jpa == null) return null;

        CategoryDomain domain = new CategoryDomain();
        domain.setId(jpa.getId());
        domain.setName(jpa.getName());
        domain.setDescription(jpa.getDescription());
        domain.setActive(jpa.getActive());
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());
        return domain;
    }
}