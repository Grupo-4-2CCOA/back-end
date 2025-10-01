package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.category.*;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoryController {

  @GetMapping("/{id}")
  public ResponseEntity<GetCategoryResponseDto> getCategory(@PathVariable Integer id) {
      GetCategoryResponseDto getCategoryResponseDto = new GetCategoryResponseDto(
              id,
              "Categoria de teste",
              "acho que tá funcionando"
      );

      return ResponseEntity.status(200).body(getCategoryResponseDto);
  }

  @PostMapping
  public ResponseEntity<CreateCategoryResponseDto> createCategory(@RequestBody @Valid CreateCategoryRequestDto createCategoryRequestDto) {
    CreateCategoryResponseDto createCategoryResponseDto = new CreateCategoryResponseDto(
            1,
            createCategoryRequestDto.name(),
            createCategoryRequestDto.description()
    );

    return ResponseEntity.status(201).body(createCategoryResponseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UpdateCategoryResponseDto> updateCategory(@PathVariable Integer id, @RequestBody UpdateCategoryRequestDto updateCategoryRequestDto) {
      UpdateCategoryResponseDto updateCategoryResponseDto = new UpdateCategoryResponseDto(
              id,
              updateCategoryRequestDto.name(),
              updateCategoryRequestDto.description()
      );

      return ResponseEntity.status(200).body(updateCategoryResponseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
      return ResponseEntity.status(204).build();
      // O que você espera que eu retorne aqui?
  }
}
