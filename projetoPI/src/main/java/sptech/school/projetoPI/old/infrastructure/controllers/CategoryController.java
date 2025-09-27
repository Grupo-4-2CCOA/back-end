package sptech.school.projetoPI.old.infrastructure.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.core.application.usecases.category.*;
import sptech.school.projetoPI.old.core.application.usecases.category.*;
import sptech.school.projetoPI.old.core.domains.CategoryDomain;
import sptech.school.projetoPI.old.core.application.dto.category.CategoryRequestDto;
import sptech.school.projetoPI.old.core.application.dto.category.CategoryResponseDto;
import sptech.school.projetoPI.old.core.application.dto.category.CategoryResumeResponseDto;
import sptech.school.projetoPI.old.infrastructure.mappers.CategoryMapper;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.ErroResponseExamples;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Tag(name = "Categoria", description = "Endpoints para gerenciar as categorias")
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final GetAllCategoryUseCase getAllCategoriesUseCase;
    private final GetCategoryByIdUseCase getCategoryByIdUseCase;
    private final UpdateCategoryByIdUseCase updateCategoryByIdUseCase;
    private final DeleteCategoryByIdUseCase deleteCategoryByIdUseCase;

    @SecurityRequirement(name = "Bearer")
    @PostMapping
    @Operation(summary = "Cadastrar uma categoria", description = "Cadastra uma categoria no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "409", description = "Categoria já existe", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)
            ))
    })
    public ResponseEntity<CategoryResumeResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto requestDto) {
        CategoryDomain categoryDomain = CategoryMapper.toDomain(requestDto);
        CategoryDomain createdCategoryDomain = createCategoryUseCase.execute(categoryDomain);
        return new ResponseEntity<>(CategoryMapper.toResumeResponseDto(createdCategoryDomain), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Buscar categoria", description = "Busca todos as categorias cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorias trazidas com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<List<CategoryResumeResponseDto>> getAllCategories() {
        List<CategoryDomain> categories = getAllCategoriesUseCase.execute();
        List<CategoryResumeResponseDto> responseDtos = categories.stream()
                .map(CategoryMapper::toResumeResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Busca a categoria com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            ))
    })
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Integer id) {
        CategoryDomain categoryDomain = getCategoryByIdUseCase.execute(id);
        return ResponseEntity.ok(CategoryMapper.toResponseDto(categoryDomain));
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria por ID", description = "Atualiza uma categoria com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<CategoryResumeResponseDto> updateCategoryById(
            @Valid @RequestBody CategoryRequestDto requestDto,
            @PathVariable Integer id) {

        CategoryDomain categoryDomain = CategoryMapper.toDomain(requestDto);
        CategoryDomain updatedCategoryDomain = updateCategoryByIdUseCase.execute(categoryDomain,id);
        return ResponseEntity.ok(CategoryMapper.toResumeResponseDto(updatedCategoryDomain));
    }

    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar categoria por ID", description = "Deleta uma categoria com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public void deleteCategoryById(@PathVariable Integer id) {
        deleteCategoryByIdUseCase.execute(id);
    }
}