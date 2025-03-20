package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.entities.Appointment;
import sptech.school.projetoPI.services.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/atendimentos")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService appointmentService) {
        this.service = appointmentService;
    }

    @PostMapping
    public ResponseEntity<Appointment> signAppointment(@Valid @RequestBody Appointment appointment) {
        return ResponseEntity.status(201).body(service.signAppointment(appointment));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = service.getAllAppointments();

        if (appointments.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.getAppointmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointmentById(@Valid @RequestBody Appointment appointment, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.updateAppointmentById(appointment, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointmentById(@PathVariable Integer id) {
        return service.deleteAppointmentById(id);
    }
}
