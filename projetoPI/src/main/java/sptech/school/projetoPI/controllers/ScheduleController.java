package sptech.school.projetoPI.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<Schedule> signSchedule(@RequestBody Schedule schedule) {
        return ResponseEntity.status(201).body(service.signSchedule(schedule));
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = service.getAllSchedules();

        if (schedules.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(schedules);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.getScheduleById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateScheduleById(@RequestBody Schedule schedule, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.updateScheduleById(schedule, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScheduleById(@PathVariable Integer id) {
        return service.deleteScheduleById(id);
    }
}
