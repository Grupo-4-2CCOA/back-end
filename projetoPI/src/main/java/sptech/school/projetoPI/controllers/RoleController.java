package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.role.RoleMapper;
import sptech.school.projetoPI.dto.role.RoleRequestDto;
import sptech.school.projetoPI.dto.role.RoleResponseDto;
import sptech.school.projetoPI.dto.role.RoleResumeResponseDto;
import sptech.school.projetoPI.entities.Role;
import sptech.school.projetoPI.services.RoleService;

import java.util.List;

@RestController
@RequestMapping("/cargos")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @PostMapping
    public ResponseEntity<RoleResumeResponseDto> signRole(@Valid @RequestBody RoleRequestDto category) {
        Role tempRole = service.signRole(RoleMapper.toEntity(category));
        return ResponseEntity.status(201).body(RoleMapper.toResumeResponseDto(tempRole));
    }

    @GetMapping
    public ResponseEntity<List<RoleResumeResponseDto>> getAllRoles() {
        List<Role> categories = service.getAllRoles();

        if(categories.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(categories.stream().map(RoleMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(RoleMapper.toResponseDto(service.getRoleById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResumeResponseDto> updateRoleById(@Valid @RequestBody RoleRequestDto category, @PathVariable Integer id) {
        Role tempRole = service.updateRoleById(RoleMapper.toEntity(category), id);
        return ResponseEntity.status(200).body(RoleMapper.toResumeResponseDto(tempRole));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleById(@PathVariable Integer id) {
        service.deleteRoleById(id);
        return ResponseEntity.status(204).build();
    }
}
