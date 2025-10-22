package sptech.school.projetoPI.infrastructure.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.core.application.usecases.schedule.*;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleRequestDto;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.di.RabbitMqConfig;
import sptech.school.projetoPI.infrastructure.mappers.ScheduleMapper;

import java.util.List;

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
    private final GetAllSchedulesByClient getAllSchedulesByClient;

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
        return new ResponseEntity<>(HttpStatus.CREATED);
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
    public ResponseEntity<Page<ScheduleResumeResponseDto>> getAllSchedules() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        Page<ScheduleDomain> scheduleDomains = getAllScheduleUseCase.execute(pageable);
        Page<ScheduleResumeResponseDto> responseDtos = scheduleDomains.map(ScheduleMapper::toResumeResponseDto);
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

    @SecurityRequirement(name = "Bearer")
    @GetMapping("/agendamentos-por-cliente/{id}")
    @Operation(summary = "Buscar agendamento por Cliente", description = "Busca o agendamento com base no Cliente fornecido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agendamentos encontrados por cliente", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ScheduleResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.OK)
            )),
            @ApiResponse(responseCode = "404", description = "Agendamentos não encontrados por cliente", content = @Content(
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
    public ResponseEntity<Page<ScheduleDomain>> getAllScheduleByClient(@PathVariable Integer id) {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(getAllSchedulesByClient.execute(pageable, id));
    }
}
