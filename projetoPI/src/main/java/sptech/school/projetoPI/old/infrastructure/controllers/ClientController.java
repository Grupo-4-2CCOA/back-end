package sptech.school.projetoPI.old.infrastructure.controllers;

import org.springframework.security.oauth2.jwt.Jwt;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.core.application.usecases.client.*;
import sptech.school.projetoPI.old.core.application.usecases.client.*;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.old.core.domains.ClientDomain;
import sptech.school.projetoPI.old.core.application.dto.client.ClientRequestDto;
import sptech.school.projetoPI.old.core.application.dto.client.ClientResponseDto;
import sptech.school.projetoPI.old.core.application.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.old.infrastructure.mappers.ClientMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Cliente", description = "Endpoints para gerenciar clientes")
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final GetAllClientsUseCase getAllClientsUseCase;
    private final GetClientByIdUseCase getClientByIdUseCase;
    private final UpdateClientByIdUseCase updateClientByIdUseCase;
    private final DeleteClientByIdUseCase deleteClientByIdUseCase;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, String>> getClientDashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String nome = "Usuário";
        String email = authentication.getName();
        String role = "USER";

        if (authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            nome = (String) jwt.getClaims().get("name");
            role = (String) jwt.getClaims().get("role");
        } else if (authentication.getPrincipal() instanceof String) {
            email = authentication.getPrincipal().toString();
        }

        Map<String, String> response = new HashMap<>();
        response.put("nome", nome);
        response.put("email", email);
        response.put("tipoUsuario", role);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Cadastrar um cliente", description = "Cadastra um novo cliente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "409", description = "Usuário já existe", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)
            ))
    })
    public ResponseEntity<ClientResumeResponseDto> createClient(@Valid @RequestBody ClientRequestDto requestDto) {
        ClientDomain clientDomain = ClientMapper.toDomain(requestDto);
        ClientDomain createdClientDomain = createClientUseCase.execute(clientDomain);
        return new ResponseEntity<>(ClientMapper.toResumeResponseDto(createdClientDomain), HttpStatus.CREATED);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar clientes", description = "Busca todos os clientes cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes trazidos com sucesso", content = @Content(
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
    public ResponseEntity<List<ClientResumeResponseDto>> getAllClients() {
        List<ClientDomain> clientDomains = getAllClientsUseCase.execute();
        List<ClientResumeResponseDto> responseDtos = clientDomains.stream()
                .map(ClientMapper::toResumeResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar cliente por ID", description = "Busca o cliente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(
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
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Integer id) {
        ClientDomain clientDomain = getClientByIdUseCase.execute(id);
        return ResponseEntity.ok(ClientMapper.toResponseDto(clientDomain));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar cliente por ID", description = "Atualiza um cliente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(
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
    public ResponseEntity<ClientResumeResponseDto> updateClientById(@Valid @RequestBody ClientRequestDto requestDto, @PathVariable Integer id) {
        ClientDomain clientDomain = ClientMapper.toDomain(requestDto);
        ClientDomain updatedClientDomain = updateClientByIdUseCase.execute(clientDomain, id);
        return ResponseEntity.ok(ClientMapper.toResumeResponseDto(updatedClientDomain));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deletar cliente por ID", description = "Deleta um cliente com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso", content = @Content(
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
    public void deleteClientById(@PathVariable Integer id) {
        deleteClientByIdUseCase.execute(id);
    }
}