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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.core.application.usecases.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.core.application.usecases.schedule.*;
import sptech.school.projetoPI.core.domains.ScheduleDomain;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleRequestDto;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.mappers.ScheduleMapper;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints para gerenciar agendamentos")
public class ScheduleController {

        private static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);

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
                        @ApiResponse(responseCode = "201", description = "Agendamento cadastrado com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.CREATED))),
                        @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST))),
                        @ApiResponse(responseCode = "409", description = "Horário indisponível!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.CONFLICT)))
        })
        public ResponseEntity<ScheduleResponseDto> createSchedule(@Valid @RequestBody ScheduleRequestDto requestDto)
                        throws Exception {
                ScheduleDomain scheduleDomain = ScheduleMapper.toDomain(requestDto);
                ScheduleDomain created = createScheduleUseCase.execute(scheduleDomain);
                return new ResponseEntity<>(ScheduleMapper.toResponseDto(created), HttpStatus.CREATED);
        }

        @SecurityRequirement(name = "Bearer")
        @GetMapping
        @Operation(summary = "Buscar agendamentos", description = "Busca todos os agendamentos cadastrados no sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Agendamentos trazidos com sucesso!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.OK))),
                        @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED))),
                        @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)))
        })
        public ResponseEntity<Page<ScheduleResponseDto>> getAllSchedules(
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(required = false) String startDate,
                        @RequestParam(required = false) String endDate,
                        @RequestParam(required = false) String dataInicio,
                        @RequestParam(required = false) String dataFim) {
                String start_param = startDate != null ? startDate : dataInicio;
                String end_param = endDate != null ? endDate : dataFim;

                logger.info("getAllSchedules - Raw params - startDate/dataInicio: '{}', endDate/dataFim: '{}'", start_param, end_param);
                LocalDateTime start = parseLocalDateTime(start_param);
                LocalDateTime end = parseLocalDateTime(end_param);

                start = start != null? start.toLocalDate().atStartOfDay() : null;
                end = end != null? end.toLocalDate().atTime(23, 59, 59) : null;

                logger.info("getAllSchedules - Parsed dates - startDate: {}, endDate: {}", start, end);
                int size = 5;
                Pageable pageable = PageRequest.of(page, size);

                Page<ScheduleDomain> scheduleDomains = getAllScheduleUseCase.execute(pageable, start, end);
                Page<ScheduleResponseDto> responseDtos = scheduleDomains.map(ScheduleMapper::toResponseDto);
                return ResponseEntity.ok(responseDtos);
        }

        private LocalDateTime parseLocalDateTime(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                        logger.debug("parseLocalDateTime - dateString is null or empty");
                        return null;
                }

                logger.debug("parseLocalDateTime - Trying to parse: '{}'", dateString);

                java.time.format.DateTimeFormatter[] formatters = {
                        java.time.format.DateTimeFormatter.ISO_DATE_TIME,
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                        java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"),
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
                };

                for (java.time.format.DateTimeFormatter formatter : formatters) {
                        try {
                                String pattern = formatter.toString();
                                if (dateString.trim().matches("\\d{4}-\\d{2}-\\d{2}") ||
                                    dateString.trim().matches("\\d{2}/\\d{2}/\\d{4}")) {
                                        java.time.LocalDate date = java.time.LocalDate.parse(dateString.trim(), formatter);
                                        LocalDateTime result = date.atStartOfDay();
                                        logger.debug("parseLocalDateTime - Successfully parsed with format: {} (converted to LocalDateTime)", formatter);
                                        return result;
                                } else {
                                        LocalDateTime result = LocalDateTime.parse(dateString.trim(), formatter);
                                        logger.debug("parseLocalDateTime - Successfully parsed with format: {}", formatter);
                                        return result;
                                }
                        } catch (Exception e) {
                                logger.debug("parseLocalDateTime - Failed with format: {}", formatter);
                        }
                }

                logger.warn("parseLocalDateTime - Could not parse date: '{}'", dateString);
                throw new IllegalArgumentException("Formato de data não suportado: " + dateString);
        }

        @SecurityRequirement(name = "Bearer")
        @GetMapping("/{id}")
        @Operation(summary = "Buscar agendamento por ID", description = "Busca o agendamento com base no ID fornecido.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Agendamento encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.OK))),
                        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND))),
                        @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED))),
                        @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)))
        })
        public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Integer id) {
                ScheduleDomain scheduleDomain = getScheduleByIdUseCase.execute(id);
                return ResponseEntity.ok(ScheduleMapper.toResponseDto(scheduleDomain));
        }

        @SecurityRequirement(name = "Bearer")
        @PutMapping("/{id}")
        @Operation(summary = "Atualizar agendamento por ID", description = "Atualiza um agendamento com base no ID fornecido.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Agendamento atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.OK))),
                        @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST))),
                        @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED))),
                        @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)))
        })
        public ResponseEntity<ScheduleResumeResponseDto> updateScheduleById(
                        @Valid @RequestBody ScheduleRequestDto requestDto, @PathVariable Integer id) {
                ScheduleDomain scheduleDomain = ScheduleMapper.toDomain(requestDto);
                ScheduleDomain updated = updateScheduleByIdUseCase.execute(scheduleDomain, id);
                return ResponseEntity.ok(ScheduleMapper.toResumeResponseDto(updated));
        }

        @SecurityRequirement(name = "Bearer")
        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @Operation(summary = "Deletar agendamento por ID", description = "Deleta um agendamento com base no ID fornecido.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Agendamento removido com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.NO_CONTENT))),
                        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND))),
                        @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED))),
                        @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResumeResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)))
        })
        public void deleteScheduleById(@PathVariable Integer id) {
                deleteScheduleByIdUseCase.execute(id);
        }

        @SecurityRequirement(name = "Bearer")
        @GetMapping("/servicos-por-agendamento-id/{id}")
        @Operation(summary = "Buscar agendamento por ID", description = "Busca o agendamento com base no ID fornecido.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Agendamento encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.OK))),
                        @ApiResponse(responseCode = "404", description = "Agendamento não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND))),
                        @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED))),
                        @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)))
        })
        public ResponseEntity<List<String>> getServiceNamesByScheduleId(@PathVariable Integer id) {
                return ResponseEntity.ok(getServiceNamesByScheduleIdUseCase.execute(id));
        }

        @SecurityRequirement(name = "Bearer")
        @GetMapping("/agendamentos-por-cliente/{id}")
        @Operation(summary = "Buscar agendamento por Cliente", description = "Busca o agendamento com base no Cliente fornecido.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Agendamentos encontrados por cliente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.OK))),
                        @ApiResponse(responseCode = "404", description = "Agendamentos não encontrados por cliente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.NOT_FOUND))),
                        @ApiResponse(responseCode = "401", description = "Acesso não autorizado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.UNAUTHORIZED))),
                        @ApiResponse(responseCode = "403", description = "Acesso proibido", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ScheduleResponseDto.class), examples = @ExampleObject(value = ErroResponseExamples.FORBIDDEN)))
        })
        public ResponseEntity<Page<ScheduleResponseDto>> getAllScheduleByClient(
                        @PathVariable Integer id,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(required = false) String startDate,
                        @RequestParam(required = false) String endDate,
                        @RequestParam(required = false) String dataInicio,
                        @RequestParam(required = false) String dataFim) {
                // Aceita ambos os nomes de parâmetros
                String start_param = startDate != null ? startDate : dataInicio;
                String end_param = endDate != null ? endDate : dataFim;

                logger.info("getAllScheduleByClient - Raw params - clientId: {}, startDate/dataInicio: '{}', endDate/dataFim: '{}'", id, start_param, end_param);
                LocalDateTime start = parseLocalDateTime(start_param);
                LocalDateTime end = parseLocalDateTime(end_param);

                start = start != null? start.toLocalDate().atStartOfDay() : null;
                end = end != null? end.toLocalDate().atTime(23, 59, 59) : null;

                logger.info("getAllScheduleByClient - Parsed dates - clientId: {}, startDate: {}, endDate: {}", id, start, end);
                int size = 5;
                Pageable pageable = PageRequest.of(page, size);

                Page<ScheduleDomain> scheduleDomains = getAllSchedulesByClient.execute(pageable, id, start, end);
                Page<ScheduleResponseDto> responseDtos = scheduleDomains.map(ScheduleMapper::toResponseDto);
                return ResponseEntity.ok(responseDtos);
        }
}
