package sptech.school.projetoPI.controllers;

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
public class ScheduleController {

    private final ScheduleService service;

    @PostMapping
    public ResponseEntity<ScheduleResumeResponseDto> signSchedule(@Valid @RequestBody ScheduleRequestDto schedule) {
        Schedule tempSchedule = service.signSchedule(ScheduleMapper.toEntity(schedule));
        return ResponseEntity.status(201).body(ScheduleMapper.toResumeResponseDto(tempSchedule));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResumeResponseDto>> getAllSchedules() {
        List<Schedule> schedules = service.getAllSchedules();

        if (schedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(schedules.stream().map(ScheduleMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ScheduleMapper.toResponseDto(service.getScheduleById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResumeResponseDto> updateScheduleById(@Valid @RequestBody ScheduleUpdateRequestDto schedule, @PathVariable Integer id) {
        Schedule tempSchedule = service.updateScheduleById(ScheduleMapper.toEntity(schedule), id);
        return ResponseEntity.status(200).body(ScheduleMapper.toResumeResponseDto(tempSchedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable Integer id) {
        service.deleteScheduleById(id);
        return ResponseEntity.status(204).build();
    }
}
