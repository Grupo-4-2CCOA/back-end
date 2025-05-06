package sptech.school.projetoPI.controllers;

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
import sptech.school.projetoPI.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.dto.role.RoleMapper;
import sptech.school.projetoPI.dto.role.RoleRequestDto;
import sptech.school.projetoPI.dto.role.RoleResponseDto;
import sptech.school.projetoPI.dto.role.RoleResumeResponseDto;
import sptech.school.projetoPI.entities.Role;
import sptech.school.projetoPI.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/cargos")
@RequiredArgsConstructor
@Tag(name = "Cargos", description = "Endpoints para gerenciar os cargos")
public class RoleController {

    private final RoleService service;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar um cargo", description = "Cadastra um novo cargo no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cargo cadastrado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
    })
    public ResponseEntity<RoleResumeResponseDto> signRole(@Valid @RequestBody RoleRequestDto category) {
        Role tempRole = service.signRole(RoleMapper.toEntity(category));
        return ResponseEntity.status(201).body(RoleMapper.toResumeResponseDto(tempRole));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar cargos", description = "Busca todos os cargos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "cargos trazidos com sucesso", content = @Content(
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
    public ResponseEntity<List<RoleResumeResponseDto>> getAllRoles() {
        List<Role> categories = service.getAllRoles();

        if(categories.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(categories.stream().map(RoleMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar cargos por ID", description = "Busca o cargos com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "cargos encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "cargos não encontrado", content = @Content(
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
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(RoleMapper.toResponseDto(service.getRoleById(id)));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar cargos por ID", description = "Atualiza um cargo com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargo atualizado com sucesso", content = @Content(
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
    public ResponseEntity<RoleResumeResponseDto> updateRoleById(@Valid @RequestBody RoleRequestDto category, @PathVariable Integer id) {
        Role tempRole = service.updateRoleById(RoleMapper.toEntity(category), id);
        return ResponseEntity.status(200).body(RoleMapper.toResumeResponseDto(tempRole));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deletar cargos por ID", description = "Deleta um cargos com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "cargos não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "cargos removido com sucesso", content = @Content(
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
    public ResponseEntity<Void> deleteRoleById(@PathVariable Integer id) {
        service.deleteRoleById(id);
        return ResponseEntity.status(204).build();
    }
}
