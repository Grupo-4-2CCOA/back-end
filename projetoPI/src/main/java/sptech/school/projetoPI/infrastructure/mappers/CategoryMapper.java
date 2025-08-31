package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryRequestDto;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryResponseDto;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.CategoryJpaEntity;

public class CategoryMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static Category toDomain(CategoryRequestDto requestObject) {
        if (requestObject == null) return null;

        Category category = new Category();
        category.setName(requestObject.getName());
        category.setDescription(requestObject.getDescription());
        category.setActive(true);
        return category;
    }

    /* ========= DOMAIN -> DTO (Completo) ========= */
    public static CategoryResponseDto toResponseDto(Category domain) {
        if (domain == null) return null;

        return CategoryResponseDto.builder()
                .id(domain.getId())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .name(domain.getName())
                .description(domain.getDescription())
                .build();
    }

    /* ========= DOMAIN -> DTO (Resumo) ========= */
    public static CategoryResumeResponseDto toResumeResponseDto(Category domain) {
        if (domain == null) return null;

        return CategoryResumeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    /* ========= DOMAIN -> JPA (Para salvar no banco) ========= */
    public static CategoryJpaEntity toJpaEntity(Category domain) {
        if (domain == null) return null;

        return CategoryJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .active(domain.getActive())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    /* ========= JPA -> DOMAIN (Para ler do banco) ========= */
    public static Category toDomain(CategoryJpaEntity jpa) {
        if (jpa == null) return null;

        Category domain = new Category();
        domain.setId(jpa.getId());
        domain.setName(jpa.getName());
        domain.setDescription(jpa.getDescription());
        domain.setActive(jpa.getActive());
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());
        return domain;
    }
}