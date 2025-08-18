package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryRequestDto;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryResponseDto;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryResumeResponseDto;

public class CategoryMapper {
    public static Category toEntity(CategoryRequestDto requestObject) {
        if(requestObject == null) return null;

        return Category.builder()
                .name(requestObject.getName())
                .description(requestObject.getDescription())
                .active(true)
                .build();
    }

    public static CategoryResponseDto toResponseDto(Category entity) {
        if(entity == null) return null;

        return CategoryResponseDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public static CategoryResumeResponseDto toResumeResponseDto(Category entity) {
        if(entity == null) return null;

        return CategoryResumeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
