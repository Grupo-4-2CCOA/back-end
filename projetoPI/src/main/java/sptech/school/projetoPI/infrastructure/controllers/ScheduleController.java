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
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleMapper;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleRequestDto;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints para gerenciar agendamentos")
public class ScheduleController extends AbstractController<Schedule, ScheduleRequestDto, ScheduleResponseDto, ScheduleResumeResponseDto> {

    private final ScheduleService service;

    @Override
    public ScheduleService getService() {
        return service;
    }

    @Override
    public Schedule toEntity(ScheduleRequestDto requestDto) {
        return ScheduleMapper.toEntity(requestDto);
    }

    @Override
    public ScheduleResponseDto toResponse(Schedule entity) {
        return ScheduleMapper.toResponseDto(entity);
    }

    @Override
    public ScheduleResumeResponseDto toResumeResponse(Schedule entity) {
        return ScheduleMapper.toResumeResponseDto(entity);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastrar agendamento", description = "Cadastra um novo agendamento no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agendamento cadastrado com sucesso!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "409", description = "Horário indisponível!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)
            ))
    })
    public ResponseEntity<ScheduleResumeResponseDto> postMethod(@Valid @RequestBody ScheduleRequestDto requestDto) {
        return super.postMethod(requestDto);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar agendamentos", description = "Busca todos os agendamentos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos trazidos com sucesso!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<List<ScheduleResumeResponseDto>> getAllMethod() {
        return super.getAllMethod();
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Buscar agendamento por ID", description = "Busca o agendamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<ScheduleResponseDto> getByIdMethod(@PathVariable Integer id) {
        return super.getByIdMethod(id);
    }

    @Override
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualizar agendamento por ID", description = "Atualiza um agendamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<ScheduleResumeResponseDto> putByIdMethod(@Valid @RequestBody ScheduleRequestDto requestDto, @PathVariable Integer id) {
        return super.putByIdMethod(requestDto, id);
    }

    @Override
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deletar agendamento por ID ", description = "Deleta um agendamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "204", description = "Agendamento removido com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            ))
    })
    public ResponseEntity<Void> deleteByIdMethod(@PathVariable Integer id) {
        return super.deleteByIdMethod(id);
    }
}
