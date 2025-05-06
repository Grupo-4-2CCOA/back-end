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
import sptech.school.projetoPI.dto.employee.EmployeeMapper;
import sptech.school.projetoPI.dto.employee.EmployeeRequestDto;
import sptech.school.projetoPI.dto.employee.EmployeeResponseDto;
import sptech.school.projetoPI.dto.employee.EmployeeResumeResponseDto;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.user.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
@Tag(name = "Funcionário", description = "Endpoints para gerenciar os funcionários")
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar um funcionario", description = "Cadastra um novo funcionario no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
    })
    public ResponseEntity<EmployeeResumeResponseDto> signEmployee(@Valid @RequestBody EmployeeRequestDto employee) {
        Employee tempEmployee = service.signEmployee(EmployeeMapper.toEntity(employee));
        return ResponseEntity.status(201).body(EmployeeMapper.toResumeResponseDto(tempEmployee));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar funcionario", description = "Busca todos os funcionarios cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionarios trazidos com sucesso", content = @Content(
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
    public ResponseEntity<List<EmployeeResumeResponseDto>> getAllEmployees() {
        List<Employee> Employees = service.getAllEmployees();

        if (Employees.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(Employees.stream().map(EmployeeMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar funcionario por ID", description = "Busca o funcionario com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionario encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Funcionario não encontrado", content = @Content(
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
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(EmployeeMapper.toResponseDto(service.getEmployeeById(id)));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar funcionario por ID", description = "Atualiza um funcionario com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionario atualizado com sucesso", content = @Content(
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
    public ResponseEntity<EmployeeResumeResponseDto> updateEmployeeById(@Valid @RequestBody EmployeeRequestDto Employee, @PathVariable Integer id) {
        Employee tempEmployee = service.updateEmployeeById(EmployeeMapper.toEntity(Employee), id);
        return ResponseEntity.status(200).body(EmployeeMapper.toResumeResponseDto(tempEmployee));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deletar funcionario por ID", description = "Deleta um funcionario com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Funcionario não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Funcionario removido com sucesso", content = @Content(
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
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Integer id) {
        service.deleteEmployeeById(id);
        return ResponseEntity.status(204).build();
    }
}
