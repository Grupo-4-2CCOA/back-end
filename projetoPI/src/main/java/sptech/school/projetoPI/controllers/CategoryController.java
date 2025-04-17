package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.category.CategoryMapper;
import sptech.school.projetoPI.dto.category.CategoryRequestDto;
import sptech.school.projetoPI.dto.category.CategoryResponseDto;
import sptech.school.projetoPI.dto.category.CategoryResumeResponseDto;
import sptech.school.projetoPI.entities.Category;
import sptech.school.projetoPI.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResumeResponseDto> signCategory(@Valid @RequestBody CategoryRequestDto category) {
        Category tempCategory = service.signCategory(CategoryMapper.toEntity(category));
        return ResponseEntity.status(201).body(CategoryMapper.toResumeResponseDto(tempCategory));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResumeResponseDto>> getAllCategories() {
        List<Category> categories = service.getAllCategories();

        if(categories.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(categories.stream().map(CategoryMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(CategoryMapper.toResponseDto(service.getCategoryById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResumeResponseDto> updateCategoryById(@Valid @RequestBody CategoryRequestDto category, @PathVariable Integer id) {
        Category tempCategory = service.updateCategoryById(CategoryMapper.toEntity(category), id);
        return ResponseEntity.status(200).body(CategoryMapper.toResumeResponseDto(tempCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Integer id) {
        service.deleteCategoryById(id);
        return ResponseEntity.status(204).build();
    }
}
