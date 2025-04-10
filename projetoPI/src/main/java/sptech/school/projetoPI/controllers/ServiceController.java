package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.service.ServiceMapper;
import sptech.school.projetoPI.dto.service.ServiceRequestDto;
import sptech.school.projetoPI.dto.service.ServiceResponseDto;
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
    public ResponseEntity<ServiceResponseDto> signService(@Valid @RequestBody ServiceRequestDto service1) {
        Service tempService = service.signService(ServiceMapper.toEntity(service1));
        return ResponseEntity.status(201).body(ServiceMapper.toResponseDto(tempService));
    }

    @GetMapping
    public ResponseEntity<List<ServiceResponseDto>> getAllServices() {
        List<Service> services = service.getAllServices();

        if (services.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(services.stream().map(ServiceMapper::toResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> getServicoById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ServiceMapper.toResponseDto(service.getServiceById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> updateServicoById(@Valid @RequestBody ServiceRequestDto service1, @PathVariable Integer id) {
        Service tempService = service.signService(ServiceMapper.toEntity(service1));
        return ResponseEntity.status(200).body(ServiceMapper.toResponseDto(tempService));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoById(@PathVariable Integer id) {
        return service.deleteServiceById(id);
    }
}
