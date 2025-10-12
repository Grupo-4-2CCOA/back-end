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
import sptech.school.projetoPI.core.application.dto.user.UserRequestDto;
import sptech.school.projetoPI.core.application.dto.user.UserResponseDto;
import sptech.school.projetoPI.core.application.dto.user.UserResumeResponseDto;
import sptech.school.projetoPI.core.application.usecases.user.employee.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.infrastructure.mappers.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
@Tag(name = "Funcionário", description = "Endpoints para gerenciar os funcionários")
public class EmployeeController {

    private final CreateEmployeeUseCase createEmployeeUseCase;
    private final GetAllEmployeeUseCase getAllEmployeesUseCase;
    private final GetEmployeeByIdUseCase getEmployeeByIdUseCase;
    private final UpdateEmployeeByIdUseCase updateEmployeeByIdUseCase;
    private final DeleteEmployeeByIdUseCase deleteEmployeeByIdUseCase;

    @SecurityRequirement(name = "Bearer")
    @PostMapping
    @Operation(summary = "Cadastrar um funcionario", description = "Cadastra um novo funcionario no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "409", description = "Funcionário já existe", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)
            ))
    })
    public ResponseEntity<UserResumeResponseDto> createEmployee(@Valid @RequestBody UserRequestDto requestDto) {
        UserDomain employeeDomain = UserMapper.toDomain(requestDto);
        UserDomain createdEmployeeDomain = createEmployeeUseCase.execute(employeeDomain);
        return new ResponseEntity<>(UserMapper.toResumeResponseDto(createdEmployeeDomain), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Buscar funcionario", description = "Busca todos os funcionarios cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionarios trazidos com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<List<UserResumeResponseDto>> getAllEmployees() {
        List<UserDomain> employeeDomains = getAllEmployeesUseCase.execute();
        List<UserResumeResponseDto> responseDtos = employeeDomains.stream()
                .map(UserMapper::toResumeResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar funcionario por ID", description = "Busca o funcionario com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionario encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Funcionario não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<UserResponseDto> getEmployeeById(@PathVariable Integer id) {
        UserDomain employeeDomain = getEmployeeByIdUseCase.execute(id);
        return ResponseEntity.ok(UserMapper.toResponseDto(employeeDomain));
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar funcionario por ID", description = "Atualiza um funcionario com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionario atualizado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<UserResumeResponseDto> updateEmployeeById(
            @Valid @RequestBody UserRequestDto requestDto,
            @PathVariable Integer id) {
        UserDomain employeeDomain = UserMapper.toDomain(requestDto);
        UserDomain updatedEmployeeDomain = updateEmployeeByIdUseCase.execute(employeeDomain, id);
        return ResponseEntity.ok(UserMapper.toResumeResponseDto(updatedEmployeeDomain));
    }

    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar funcionario por ID", description = "Deleta um funcionario com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Funcionario não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Funcionario removido com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public void deleteEmployeeById(@PathVariable Integer id) {
        deleteEmployeeByIdUseCase.execute(id);
    }
}