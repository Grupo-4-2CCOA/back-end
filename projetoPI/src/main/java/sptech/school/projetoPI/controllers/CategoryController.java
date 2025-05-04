package sptech.school.projetoPI.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.category.CategoryMapper;
import sptech.school.projetoPI.dto.category.CategoryRequestDto;
import sptech.school.projetoPI.dto.category.CategoryResponseDto;
import sptech.school.projetoPI.dto.category.CategoryResumeResponseDto;
import sptech.school.projetoPI.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.entities.Category;
import sptech.school.projetoPI.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    @Operation(summary = "Cadastrar uma categoria", description = "Cadastra uma categoria no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos"),
    })
    public ResponseEntity<CategoryResumeResponseDto> signCategory(@Valid @RequestBody CategoryRequestDto category) {
        Category tempCategory = service.signCategory(CategoryMapper.toEntity(category));
        return ResponseEntity.status(201).body(CategoryMapper.toResumeResponseDto(tempCategory));
    }

    @GetMapping
    @Operation(summary = "Buscar categoria", description = "Busca todos as categoria cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria trazidos com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<List<CategoryResumeResponseDto>> getAllCategories() {
        List<Category> categories = service.getAllCategories();

        if(categories.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(categories.stream().map(CategoryMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Busca o categoria com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(CategoryMapper.toResponseDto(service.getCategoryById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria por ID", description = "Atualiza uma categoria com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<CategoryResumeResponseDto> updateCategoryById(@Valid @RequestBody CategoryRequestDto category, @PathVariable Integer id) {
        Category tempCategory = service.updateCategoryById(CategoryMapper.toEntity(category), id);
        return ResponseEntity.status(200).body(CategoryMapper.toResumeResponseDto(tempCategory));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar categoria por ID", description = "Deleta uma categoria com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Categoria removido com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Integer id) {
        service.deleteCategoryById(id);
        return ResponseEntity.status(204).build();
    }
}
