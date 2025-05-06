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
import sptech.school.projetoPI.dto.availability.AvailabilityMapper;
import sptech.school.projetoPI.dto.availability.AvailabilityRequestDto;
import sptech.school.projetoPI.dto.availability.AvailabilityResponseDto;
import sptech.school.projetoPI.dto.availability.AvailabilityResumeResponseDto;
import sptech.school.projetoPI.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.entities.Availability;
import sptech.school.projetoPI.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.AvailabilityService;

import java.util.List;

@RestController
@RequestMapping("/disponibilidades")
@RequiredArgsConstructor
@Tag(name = "Disponibilidade de horários", description = "Endpoints para gerenciar a disponibilidade de horários")
public class AvailabilityController {

    private final AvailabilityService service;

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar uma disponibilidade", description = "Cadastra uma nova disponibilidade no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disponibilidade cadastrada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "409", description = "Disponibilidade já existe", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)
            ))
    })
    public ResponseEntity<AvailabilityResumeResponseDto> signAvailability(@Valid @RequestBody AvailabilityRequestDto availability) {
        Availability tempAvailability = service.signAvailability(AvailabilityMapper.toEntity(availability));
        return ResponseEntity.status(201).body(AvailabilityMapper.toResumeResponseDto(tempAvailability));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar disponibilidade", description = "Busca todas as disponibilidades cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidades trazidos com sucesso", content = @Content(
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
    public ResponseEntity<List<AvailabilityResumeResponseDto>> getAllAvailabilities() {
        List<Availability> availabilities = service.getAllAvailabilities();

        if(availabilities.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(availabilities.stream().map(AvailabilityMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar disponibilidade por ID", description = "Busca a disponibilidade com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidades encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Disponibilidades não encontrado", content = @Content(
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
    public ResponseEntity<AvailabilityResponseDto> getAvailabilityById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(AvailabilityMapper.toResponseDto(service.getAvailabilityById(id)));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar disponibilidade por ID", description = "Atualiza uma disponibilidade com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidades atualizado com sucesso", content = @Content(
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
    public ResponseEntity<AvailabilityResumeResponseDto> updateAvailabilityById(@Valid @RequestBody AvailabilityRequestDto availability, @PathVariable Integer id) {
        Availability tempAvailability = service.updateAvailabilityById(AvailabilityMapper.toEntity(availability), id);
        return ResponseEntity.status(200).body(AvailabilityMapper.toResumeResponseDto(tempAvailability));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deletar disponibilidade por ID", description = "Deleta uma disponibilidade com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Disponibilidades não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Disponibilidades removido com sucesso", content = @Content(
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
    public ResponseEntity<Void> deleteAvailabilityById(@PathVariable Integer id) {
        service.deleteAvailabilityById(id);
        return ResponseEntity.status(204).build();
    }
}
