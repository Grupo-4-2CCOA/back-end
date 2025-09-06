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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.application.usecases.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.application.usecases.role.CreateRoleUseCase;
import sptech.school.projetoPI.application.usecases.role.DeleteRoleByIdUseCase;
import sptech.school.projetoPI.application.usecases.role.GetAllRoleUseCase;
import sptech.school.projetoPI.application.usecases.role.GetRoleByIdUseCase;
import sptech.school.projetoPI.application.usecases.role.UpdateRoleByIdUseCase;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.infrastructure.dto.role.RoleRequestDto;
import sptech.school.projetoPI.infrastructure.dto.role.RoleResponseDto;
import sptech.school.projetoPI.infrastructure.dto.role.RoleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.mappers.RoleMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cargos")
@RequiredArgsConstructor
@Tag(name = "Cargos", description = "Endpoints para gerenciar os cargos")
public class RoleController {

    private final CreateRoleUseCase createRoleUseCase;
    private final DeleteRoleByIdUseCase deleteRoleByIdUseCase;
    private final GetAllRoleUseCase getAllRoleUseCase;
    private final GetRoleByIdUseCase getRoleByIdUseCase;
    private final UpdateRoleByIdUseCase updateRoleByIdUseCase;

    @SecurityRequirement(name = "Bearer")
    @PostMapping
    @Operation(summary = "Cadastrar um cargo", description = "Cadastra um novo cargo no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cargo cadastrado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "409", description = "Cargo já existe", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)
            ))
    })
    public ResponseEntity<RoleResumeResponseDto> createRole(@Valid @RequestBody RoleRequestDto requestDto) {
        Role role = RoleMapper.toDomain(requestDto);
        Role createdRole = createRoleUseCase.execute(role);
        return new ResponseEntity<>(RoleMapper.toResumeResponseDto(createdRole), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Buscar cargos", description = "Busca todos os cargos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargos trazidos com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public ResponseEntity<List<RoleResumeResponseDto>> getAllRoles() {
        List<Role> roles = getAllRoleUseCase.execute();
        List<RoleResumeResponseDto> responseDtos = roles.stream()
                .map(RoleMapper::toResumeResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cargo por ID", description = "Busca o cargo com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargo encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Integer id) {
        Role role = getRoleByIdUseCase.execute(id);
        return ResponseEntity.ok(RoleMapper.toResponseDto(role));
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cargo por ID", description = "Atualiza um cargo com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cargo atualizado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public ResponseEntity<RoleResumeResponseDto> updateRoleById(@Valid @RequestBody RoleRequestDto requestDto,
                                                                @PathVariable Integer id) {
        Role role = RoleMapper.toDomain(requestDto);
        Role updatedRole = updateRoleByIdUseCase.execute(role, id);
        return ResponseEntity.ok(RoleMapper.toResumeResponseDto(updatedRole));
    }

    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar cargo por ID", description = "Deleta um cargo com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cargo removido com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "404", description = "Cargo não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RoleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public void deleteRoleById(@PathVariable Integer id) {
        deleteRoleByIdUseCase.execute(id);
    }
}
