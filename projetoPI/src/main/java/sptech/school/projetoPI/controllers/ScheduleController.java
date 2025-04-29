package sptech.school.projetoPI.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.schedule.*;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.services.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
@Tag(name = "Agendamentos", description = "Endpoints para gerenciar agendamentos")
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping
    @Operation(summary = "Cadastrar agendamento", description = "Cadastra um novo agendamento no sistema.")
    public ResponseEntity<ScheduleResumeResponseDto> signSchedule(@Valid @RequestBody ScheduleRequestDto schedule) {
        Schedule tempSchedule = service.signSchedule(ScheduleMapper.toEntity(schedule));
        return ResponseEntity.status(201).body(ScheduleMapper.toResumeResponseDto(tempSchedule));
    }

    @GetMapping
    @Operation(summary = "Buscar agendamentos", description = "Busca todos os agendamentos cadastrados no sistema.")
    public ResponseEntity<List<ScheduleResumeResponseDto>> getAllSchedules() {
        List<Schedule> schedules = service.getAllSchedules();

        if (schedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(schedules.stream().map(ScheduleMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar agendamento por ID", description = "Busca o agendamento com base no ID fornecido.")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ScheduleMapper.toResponseDto(service.getScheduleById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar agendamento por ID", description = "Atualiza um agendamento com base no ID fornecido.")
    public ResponseEntity<ScheduleResumeResponseDto> updateScheduleById(@Valid @RequestBody ScheduleUpdateRequestDto schedule, @PathVariable Integer id) {
        Schedule tempSchedule = service.updateScheduleById(ScheduleMapper.toEntity(schedule), id);
        return ResponseEntity.status(200).body(ScheduleMapper.toResumeResponseDto(tempSchedule));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar agendamento por ID ", description = "Deleta um agendamento com base no ID fornecido.")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable Integer id) {
        service.deleteScheduleById(id);
        return ResponseEntity.status(204).build();
    }
}
