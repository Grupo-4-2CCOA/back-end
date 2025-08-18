package sptech.school.projetoPI.infrastructure.controllers;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.controllers.AbstractController;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryMapper;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryRequestDto;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryResponseDto;
import sptech.school.projetoPI.infrastructure.dto.category.CategoryResumeResponseDto;
import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.infrastructure.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Tag(name = "Categoria", description = "Endpoints para gerenciar as categorias")
public class CategoryController extends AbstractController<Category, CategoryRequestDto, CategoryResponseDto, CategoryResumeResponseDto> {

    private final CategoryService service;

    @Override
    public CategoryService getService() {
        return service;
    }

    @Override
    public Category toEntity(CategoryRequestDto requestDto) {
        return CategoryMapper.toEntity(requestDto);
    }

    @Override
    public CategoryResponseDto toResponse(Category entity) {
        return CategoryMapper.toResponseDto(entity);
    }

    @Override
    public CategoryResumeResponseDto toResumeResponse(Category entity) {
        return CategoryMapper.toResumeResponseDto(entity);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<CategoryResumeResponseDto> postMethod(@Valid @RequestBody  CategoryRequestDto requestDto) {
        return super.postMethod(requestDto);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar categoria", description = "Busca todos as categoria cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria trazidos com sucesso", content = @Content(
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
    public ResponseEntity<List<CategoryResumeResponseDto>> getAllMethod() {
        return super.getAllMethod();
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar categoria por ID", description = "Busca o categoria com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
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
    public ResponseEntity<CategoryResponseDto> getByIdMethod(@PathVariable Integer id) {
        return super.getByIdMethod(id);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar categoria por ID", description = "Atualiza uma categoria com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizado com sucesso", content = @Content(
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
    public ResponseEntity<CategoryResumeResponseDto> putByIdMethod(@Valid @RequestBody CategoryRequestDto requestDto, @PathVariable Integer id) {
        return super.putByIdMethod(requestDto, id);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deletar categoria por ID", description = "Deleta uma categoria com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Categoria não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Categoria removido com sucesso", content = @Content(
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
    public ResponseEntity<Void> deleteByIdMethod(@PathVariable Integer id) {
        return super.deleteByIdMethod(id);
    }
}
