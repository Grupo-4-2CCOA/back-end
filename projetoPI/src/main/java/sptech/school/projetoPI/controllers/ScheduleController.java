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
import sptech.school.projetoPI.dto.schedule.*;
import sptech.school.projetoPI.dto.service.ServiceResumeResponseDto;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints para gerenciar agendamentos")
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping
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
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)
            ))
    })
    public ResponseEntity<ScheduleResumeResponseDto> signSchedule(@Valid @RequestBody ScheduleRequestDto schedule) {
        Schedule tempSchedule = service.signSchedule(ScheduleMapper.toEntity(schedule));
        return ResponseEntity.status(201).body(ScheduleMapper.toResumeResponseDto(tempSchedule));
    }

    @GetMapping
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
    public ResponseEntity<List<ScheduleResumeResponseDto>> getAllSchedules() {
        List<Schedule> schedules = service.getAllSchedules();

        if (schedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(schedules.stream().map(ScheduleMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ScheduleMapper.toResponseDto(service.getScheduleById(id)));
    }

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
    public ResponseEntity<ScheduleResumeResponseDto> updateScheduleById(@Valid @RequestBody ScheduleUpdateRequestDto schedule, @PathVariable Integer id) {
        Schedule tempSchedule = service.updateScheduleById(ScheduleMapper.toEntity(schedule), id);
        return ResponseEntity.status(200).body(ScheduleMapper.toResumeResponseDto(tempSchedule));
    }

    @DeleteMapping("/{id}")
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
    public ResponseEntity<Void> deleteScheduleById(@PathVariable Integer id) {
        service.deleteScheduleById(id);
        return ResponseEntity.status(204).build();
    }
}
