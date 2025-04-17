package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.service.ServiceMapper;
import sptech.school.projetoPI.dto.service.ServiceRequestDto;
import sptech.school.projetoPI.dto.service.ServiceResponseDto;
import sptech.school.projetoPI.dto.service.ServiceResumeResponseDto;
import sptech.school.projetoPI.entities.Service;
import sptech.school.projetoPI.services.ServiceService;

import java.util.List;

@RestController
@RequestMapping("/servicos")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService service;

    @PostMapping
    public ResponseEntity<ServiceResumeResponseDto> signService(@Valid @RequestBody ServiceRequestDto service1) {
        Service tempService = service.signService(ServiceMapper.toEntity(service1));
        return ResponseEntity.status(201).body(ServiceMapper.toResumeResponseDto(tempService));
    }

    @GetMapping
    public ResponseEntity<List<ServiceResumeResponseDto>> getAllServices() {
        List<Service> services = service.getAllServices();

        if (services.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(services.stream().map(ServiceMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceResponseDto> getServicoById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ServiceMapper.toResponseDto(service.getServiceById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceResumeResponseDto> updateServicoById(@Valid @RequestBody ServiceRequestDto service1, @PathVariable Integer id) {
        Service tempService = service.updateServiceById(ServiceMapper.toEntity(service1), id);
        return ResponseEntity.status(200).body(ServiceMapper.toResumeResponseDto(tempService));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServicoById(@PathVariable Integer id) {
        service.deleteServiceById(id);
        return ResponseEntity.status(204).build();
    }
}
