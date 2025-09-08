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
import sptech.school.projetoPI.application.usecases.availability.CreateAvailabilityUseCase;
import sptech.school.projetoPI.application.usecases.availability.DeleteAvailabilityByIdUseCase;
import sptech.school.projetoPI.application.usecases.availability.GetAllAvailabilityUseCase;
import sptech.school.projetoPI.application.usecases.availability.GetAvailabilityByIdUseCase;
import sptech.school.projetoPI.application.usecases.availability.UpdateAvailabilityByIdUseCase;
import sptech.school.projetoPI.application.usecases.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.core.domains.AvailabilityDomain;
import sptech.school.projetoPI.infrastructure.mappers.AvailabilityMapper;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityRequestDto;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityResponseDto;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityResumeResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/disponibilidades")
@RequiredArgsConstructor
@Tag(name = "Disponibilidade de horários", description = "Endpoints para gerenciar a disponibilidade de horários")
public class AvailabilityController {

    private final CreateAvailabilityUseCase createAvailabilityUseCase;
    private final DeleteAvailabilityByIdUseCase deleteAvailabilityByIdUseCase;
    private final GetAllAvailabilityUseCase getAllAvailabilityUseCase;
    private final GetAvailabilityByIdUseCase getAvailabilityByIdUseCase;
    private final UpdateAvailabilityByIdUseCase updateAvailabilityByIdUseCase;

    @SecurityRequirement(name = "Bearer")
    @PostMapping
    @Operation(summary = "Cadastrar uma disponibilidade", description = "Cadastra uma nova disponibilidade no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disponibilidade cadastrada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "409", description = "Disponibilidade já existe", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)
            ))
    })
    public ResponseEntity<AvailabilityResumeResponseDto> createAvailability(@Valid @RequestBody AvailabilityRequestDto requestDto) {
        AvailabilityDomain availabilityDomain = AvailabilityMapper.toDomain(requestDto);
        AvailabilityDomain createdAvailabilityDomain = createAvailabilityUseCase.execute(availabilityDomain);
        return new ResponseEntity<>(AvailabilityMapper.toResumeResponseDto(createdAvailabilityDomain), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Buscar disponibilidade", description = "Busca todas as disponibilidades cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidades trazidos com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public ResponseEntity<List<AvailabilityResumeResponseDto>> getAllAvailabilities() {
        List<AvailabilityDomain> availabilities = getAllAvailabilityUseCase.execute();
        List<AvailabilityResumeResponseDto> responseDtos = availabilities.stream()
                .map(AvailabilityMapper::toResumeResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar disponibilidade por ID", description = "Busca a disponibilidade com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidade encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "404", description = "Disponibilidade não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            ))
    })
    public ResponseEntity<AvailabilityResponseDto> getAvailabilityById(@PathVariable Integer id) {
        AvailabilityDomain availabilityDomain = getAvailabilityByIdUseCase.execute(id);
        return ResponseEntity.ok(AvailabilityMapper.toResponseDto(availabilityDomain));
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar disponibilidade por ID", description = "Atualiza uma disponibilidade com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidade atualizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public ResponseEntity<AvailabilityResumeResponseDto> updateAvailabilityById(@Valid @RequestBody AvailabilityRequestDto requestDto, @PathVariable Integer id) {
        AvailabilityDomain availabilityDomain = AvailabilityMapper.toDomain(requestDto);
        AvailabilityDomain updatedAvailabilityDomain = updateAvailabilityByIdUseCase.execute(id, availabilityDomain);
        return ResponseEntity.ok(AvailabilityMapper.toResumeResponseDto(updatedAvailabilityDomain));
    }

    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar disponibilidade por ID", description = "Deleta uma disponibilidade com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disponibilidade removida com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "404", description = "Disponibilidade não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            ))
    })
    public void deleteAvailabilityById(@PathVariable Integer id) {
        deleteAvailabilityByIdUseCase.execute(id);
    }
}