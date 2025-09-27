package sptech.school.projetoPI.old.infrastructure.controllers;

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
import sptech.school.projetoPI.old.core.application.usecases.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.core.application.usecases.schedule.*;
import sptech.school.projetoPI.old.core.application.usecases.schedule.*;
import sptech.school.projetoPI.old.core.domains.ScheduleDomain;
import sptech.school.projetoPI.old.core.application.dto.schedule.ScheduleRequestDto;
import sptech.school.projetoPI.old.core.application.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.old.core.application.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.old.infrastructure.mappers.ScheduleMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints para gerenciar agendamentos")
public class ScheduleController {

    private final CreateScheduleUseCase createScheduleUseCase;
    private final DeleteScheduleByIdUseCase deleteScheduleByIdUseCase;
    private final GetAllScheduleUseCase getAllScheduleUseCase;
    private final GetScheduleByIdUseCase getScheduleByIdUseCase;
    private final UpdateScheduleByIdUseCase updateScheduleByIdUseCase;
    private final GetServiceNamesByScheduleIdUseCase getServiceNamesByScheduleIdUseCase;

    @SecurityRequirement(name = "Bearer")
    @PostMapping
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
    public ResponseEntity<ScheduleResumeResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto requestDto) {
        ScheduleDomain scheduleDomain = ScheduleMapper.toDomain(requestDto);
        ScheduleDomain created = createScheduleUseCase.execute(scheduleDomain);
        return new ResponseEntity<>(ScheduleMapper.toResumeResponseDto(created), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping
    @Operation(summary = "Buscar agendamentos", description = "Busca todos os agendamentos cadastrados no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos trazidos com sucesso!", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public ResponseEntity<List<ScheduleResumeResponseDto>> getAllSchedules() {
        List<ScheduleDomain> scheduleDomains = getAllScheduleUseCase.execute();
        List<ScheduleResumeResponseDto> responseDtos = scheduleDomains.stream()
                .map(ScheduleMapper::toResumeResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDtos);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/{id}")
    @Operation(summary = "Buscar agendamento por ID", description = "Busca o agendamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Integer id) {
        ScheduleDomain scheduleDomain = getScheduleByIdUseCase.execute(id);
        return ResponseEntity.ok(ScheduleMapper.toResponseDto(scheduleDomain));
    }

    @SecurityRequirement(name = "Bearer")
    @PutMapping("/{id}")
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
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public ResponseEntity<ScheduleResumeResponseDto> updateScheduleById(@Valid @RequestBody ScheduleRequestDto requestDto, @PathVariable Integer id) {
        ScheduleDomain scheduleDomain = ScheduleMapper.toDomain(requestDto);
        ScheduleDomain updated = updateScheduleByIdUseCase.execute(scheduleDomain, id);
        return ResponseEntity.ok(ScheduleMapper.toResumeResponseDto(updated));
    }

    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletar agendamento por ID", description = "Deleta um agendamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Agendamento removido com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT)
            )),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public void deleteScheduleById(@PathVariable Integer id) {
        deleteScheduleByIdUseCase.execute(id);
    }

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/servicos-por-agendamento-id/{id}")
    @Operation(summary = "Buscar agendamento por ID", description = "Busca o agendamento com base no ID fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamento encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND)
            )),
            @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED)
            )),
            @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)
            ))
    })
    public ResponseEntity<List<String>> getServiceNamesByScheduleId(@PathVariable Integer id) {
        return ResponseEntity.ok(getServiceNamesByScheduleIdUseCase.execute(id));
    }
}
