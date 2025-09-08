package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryRequestDto;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryResponseDto;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.CategoryJpaEntity;

public class CategoryMapper {

    /* ========= DTO -> DOMAIN (Controller to Use Case) ========= */
    public static Category toDomain(CategoryRequestDto requestObject) {
        if (requestObject == null) return null;

        Category category = new Category();
        category.setName(requestObject.getName());
        category.setDescription(requestObject.getDescription());
        category.setActive(true);
        return category;
    }

    public static Category toDomain(Integer id) {
        if (id == null) return null;

        Category category = new Category();
        category.setId(id);
        return category;
    }

    /* ========= DOMAIN -> DTO (Full Response) ========= */
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

    /* ========= DOMAIN -> DTO (Resume Response) ========= */
    public static CategoryResumeResponseDto toResumeResponseDto(Category domain) {
        if (domain == null) return null;

        return CategoryResumeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    //---------------------------------------------------------
    // Mapeamento bidirecional para a camada de persistência (JPA)

    /* ========= DOMAIN -> JPA (Use Case to Repository) ========= */
    public static CategoryJpaEntity toJpaEntity(Category domain) {
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

    /* ========= JPA -> DOMAIN (Repository to Use Case) ========= */
    public static Category toDomain(CategoryJpaEntity jpa) {
        if (jpa == null) return null;

        Category domain = new Category();
        domain.setId(jpa.getId());
        domain.setName(jpa.getName());
        domain.setDescription(jpa.getDescription());
        // A propriedade 'active' foi corrigida para usar o getter da entidade JPA
        domain.setActive(jpa.getActive());
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());
        return domain;
    }
}