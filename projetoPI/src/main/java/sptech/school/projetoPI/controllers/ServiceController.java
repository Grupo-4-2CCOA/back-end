package sptech.school.projetoPI.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Serviços", description = "Endpoints para gerenciar serviços")
public class ServiceController {

    private final ServiceService service;

    @PostMapping
    @Operation(summary = "Cadastrar novo serviço", description = "Cadastra um novo serviço a partir do json fornecido.")
    public ResponseEntity<ServiceResumeResponseDto> signService(@Valid @RequestBody ServiceRequestDto service1) {
        Service tempService = service.signService(ServiceMapper.toEntity(service1));
        return ResponseEntity.status(201).body(ServiceMapper.toResumeResponseDto(tempService));
    }

    @GetMapping
    @Operation(summary = "Buscar todos os serviços", description = "Busca todos os serviços cadastros no sistema.")
    public ResponseEntity<List<ServiceResumeResponseDto>> getAllServices() {
        List<Service> services = service.getAllServices();

        if (services.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(services.stream().map(ServiceMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar serviço por ID", description = "Busca o serviço pelo ID fornecido.")
    public ResponseEntity<ServiceResponseDto> getServicoById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ServiceMapper.toResponseDto(service.getServiceById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar serviço por ID", description = "Atualiza as informações do serviço com base no ID fornecido.")
    public ResponseEntity<ServiceResumeResponseDto> updateServicoById(@Valid @RequestBody ServiceRequestDto service1, @PathVariable Integer id) {
        Service tempService = service.updateServiceById(ServiceMapper.toEntity(service1), id);
        return ResponseEntity.status(200).body(ServiceMapper.toResumeResponseDto(tempService));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar serviço por ID", description = "Deleta o serviço cujo ID fornecido.")
    public ResponseEntity<Void> deleteServicoById(@PathVariable Integer id) {
        service.deleteServiceById(id);
        return ResponseEntity.status(204).build();
    }
}
