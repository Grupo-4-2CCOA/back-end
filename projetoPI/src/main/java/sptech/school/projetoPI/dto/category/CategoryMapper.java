package sptech.school.projetoPI.dto.category;

import sptech.school.projetoPI.entities.Category;

public class CategoryMapper {
    public static Category toEntity(CategoryRequestDto requestObject) {
        Category category = new Category();

        category.setName(requestObject.getName());
        category.setDescription(requestObject.getDescription());

        return category;
    }

    public static CategoryResponseDto toResponseDto(Category entity) {
        CategoryResponseDto category = new CategoryResponseDto();

        category.setId(entity.getId());
        category.setCreatedAt(entity.getCreatedAt());
        category.setUpdatedAt(entity.getUpdatedAt());
        category.setName(entity.getName());
        category.setDescription(entity.getDescription());

        return category;
    }
}
