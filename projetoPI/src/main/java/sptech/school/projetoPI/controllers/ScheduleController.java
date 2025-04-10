package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.schedule.ScheduleMapper;
import sptech.school.projetoPI.dto.schedule.ScheduleRequestDto;
import sptech.school.projetoPI.dto.schedule.ScheduleResponseDto;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.services.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/agendamentos")
public class ScheduleController {

    private final ScheduleService service;

    public ScheduleController(ScheduleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> signSchedule(@Valid @RequestBody ScheduleRequestDto schedule) {
        Schedule tempSchedule = service.signSchedule(ScheduleMapper.toEntity(schedule));
        return ResponseEntity.status(201).body(ScheduleMapper.toResponseDto(tempSchedule));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        List<Schedule> schedules = service.getAllSchedules();

        if (schedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(schedules.stream().map(ScheduleMapper::toResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ScheduleMapper.toResponseDto(service.getScheduleById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateScheduleById(@Valid @RequestBody ScheduleRequestDto schedule, @PathVariable Integer id) {
        Schedule tempSchedule = service.signSchedule(ScheduleMapper.toEntity(schedule));
        return ResponseEntity.status(200).body(ScheduleMapper.toResponseDto(tempSchedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable Integer id) {
        return service.deleteScheduleById(id);
    }
}
