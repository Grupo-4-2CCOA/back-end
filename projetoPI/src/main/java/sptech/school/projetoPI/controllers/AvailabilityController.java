package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.availability.AvailabilityMapper;
import sptech.school.projetoPI.dto.availability.AvailabilityRequestDto;
import sptech.school.projetoPI.dto.availability.AvailabilityResponseDto;
import sptech.school.projetoPI.dto.availability.AvailabilityResumeResponseDto;
import sptech.school.projetoPI.entities.Availability;
import sptech.school.projetoPI.services.AvailabilityService;

import java.util.List;

@RestController
@RequestMapping("/disponibilidades")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService service;

    @PostMapping
    public ResponseEntity<AvailabilityResumeResponseDto> signAvailability(@Valid @RequestBody AvailabilityRequestDto availability) {
        Availability tempAvailability = service.signAvailability(AvailabilityMapper.toEntity(availability));
        return ResponseEntity.status(201).body(AvailabilityMapper.toResumeResponseDto(tempAvailability));
    }

    @GetMapping
    public ResponseEntity<List<AvailabilityResumeResponseDto>> getAllAvailabilities() {
        List<Availability> availabilities = service.getAllAvailabilities();

        if(availabilities.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(availabilities.stream().map(AvailabilityMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityResponseDto> getAvailabilityById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(AvailabilityMapper.toResponseDto(service.getAvailabilityById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityResumeResponseDto> updateAvailabilityById(@Valid @RequestBody AvailabilityRequestDto availability, @PathVariable Integer id) {
        Availability tempAvailability = service.updateAvailabilityById(AvailabilityMapper.toEntity(availability), id);
        return ResponseEntity.status(200).body(AvailabilityMapper.toResumeResponseDto(tempAvailability));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailabilityById(@PathVariable Integer id) {
        service.deleteAvailabilityById(id);
        return ResponseEntity.status(204).build();
    }
}
