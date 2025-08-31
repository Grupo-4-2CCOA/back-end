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
import sptech.school.projetoPI.application.usecases.client.CreateClientUseCase;
import sptech.school.projetoPI.application.usecases.client.GetAllClientsUseCase;
import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.infrastructure.mappers.ClientMapper;
import sptech.school.projetoPI.infrastructure.dto.client.ClientRequestDto;
import sptech.school.projetoPI.infrastructure.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.application.usecases.exceptions.ErroResponseExamples;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Cliente", description = "Endpoints para gerenciar clientes")
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final GetAllClientsUseCase getAllClientsUseCase;

    @PostMapping
    @Operation(summary = "Cadastrar um cliente", description = "Cadastra um novo cliente no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            ))
    })
    public ResponseEntity<ClientResumeResponseDto> postMethod(@Valid @RequestBody ClientRequestDto requestDto) {
        Client clientDomain = ClientMapper.toDomain(requestDto);
        Client savedClient = createClientUseCase.execute(clientDomain);
        return ResponseEntity.status(201).body(ClientMapper.toResumeResponseDto(savedClient));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar clientes", description = "Busca todos os clientes cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes trazidos com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            ))
    })
    public ResponseEntity<List<ClientResumeResponseDto>> getAllMethod() {
        List<Client> clients = getAllClientsUseCase.execute();
        List<ClientResumeResponseDto> dtos = clients.stream()
                .map(ClientMapper::toResumeResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}