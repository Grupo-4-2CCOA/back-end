package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.entities.Service;
import sptech.school.projetoPI.repositories.ServiceRepository;
import sptech.school.projetoPI.services.ServiceService;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServiceController {

    private final ServiceService service;

    public ServiceController(ServiceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Service> signService(@Valid @RequestBody Service service1) {
        return ResponseEntity.status(201).body(service.signService(service1));
    }

    @GetMapping
    public ResponseEntity<List<Service>> getAllServices() {
        List<Service> services = service.getAllServices();

        if (services.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(services);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Service> getServicoById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.getServiceById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateServicoById(@Valid @RequestBody Service service1, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.updateServiceById(service1, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoById(@PathVariable Integer id) {
        return service.deleteServiceById(id);
    }
}
