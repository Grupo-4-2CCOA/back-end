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
import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.infrastructure.mappers.AvailabilityMapper;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityRequestDto;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityResponseDto;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityResumeResponseDto;
import sptech.school.projetoPI.application.usecases.exceptions.ErroResponseExamples;

import java.util.List;

@RestController
@RequestMapping("/disponibilidades")
@RequiredArgsConstructor
@Tag(name = "Disponibilidade de horários", description = "Endpoints para gerenciar a disponibilidade de horários")
public class AvailabilityController extends AbstractController<Availability, AvailabilityRequestDto, AvailabilityResponseDto, AvailabilityResumeResponseDto> {

    private final AvailabilityService service;

    @Override
    public AvailabilityService getService() {
        return service;
    }

    @Override
    public Availability toEntity(AvailabilityRequestDto requestDto) {
        return AvailabilityMapper.toEntity(requestDto);
    }

    @Override
    public AvailabilityResponseDto toResponse(Availability entity) {
        return AvailabilityMapper.toResponseDto(entity);
    }

    @Override
    public AvailabilityResumeResponseDto toResumeResponse(Availability entity) {
        return AvailabilityMapper.toResumeResponseDto(entity);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
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
    public ResponseEntity<AvailabilityResumeResponseDto> postMethod(@Valid @RequestBody AvailabilityRequestDto requestDto) {
        return super.postMethod(requestDto);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar disponibilidade", description = "Busca todas as disponibilidades cadastrados no sistema.")
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
    public ResponseEntity<List<AvailabilityResumeResponseDto>> getAllMethod() {
        return super.getAllMethod();
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar disponibilidade por ID", description = "Busca a disponibilidade com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidades encontrado", content = @Content(
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
            @ApiResponse(responseCode = "404", description = "Disponibilidades não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            ))
    })
    public ResponseEntity<AvailabilityResponseDto> getByIdMethod(@PathVariable Integer id) {
        return super.getByIdMethod(id);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar disponibilidade por ID", description = "Atualiza uma disponibilidade com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Disponibilidades atualizado com sucesso", content = @Content(
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
    public ResponseEntity<AvailabilityResumeResponseDto> putByIdMethod(@Valid @RequestBody AvailabilityRequestDto requestDto, @PathVariable Integer id) {
        return super.putByIdMethod(requestDto, id);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deletar disponibilidade por ID", description = "Deleta uma disponibilidade com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Disponibilidades removido com sucesso", content = @Content(
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
            @ApiResponse(responseCode = "404", description = "Disponibilidades não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AvailabilityResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            ))
    })
    public ResponseEntity<Void> deleteByIdMethod(@PathVariable Integer id) {
        return super.deleteByIdMethod(id);
    }
}
