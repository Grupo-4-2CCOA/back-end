package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.employee.EmployeeMapper;
import sptech.school.projetoPI.dto.employee.EmployeeRequestDto;
import sptech.school.projetoPI.dto.employee.EmployeeResponseDto;
import sptech.school.projetoPI.dto.employee.EmployeeResumeResponseDto;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.services.user.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping
    public ResponseEntity<EmployeeResumeResponseDto> signEmployee(@Valid @RequestBody EmployeeRequestDto employee) {
        Employee tempEmployee = service.signEmployee(EmployeeMapper.toEntity(employee));
        return ResponseEntity.status(201).body(EmployeeMapper.toResumeResponseDto(tempEmployee));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResumeResponseDto>> getAllEmployees() {
        List<Employee> Employees = service.getAllEmployees();

        if (Employees.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(Employees.stream().map(EmployeeMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(EmployeeMapper.toResponseDto(service.getEmployeeById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResumeResponseDto> updateEmployeeById(@Valid @RequestBody EmployeeRequestDto Employee, @PathVariable Integer id) {
        Employee tempEmployee = service.updateEmployeeById(EmployeeMapper.toEntity(Employee), id);
        return ResponseEntity.status(200).body(EmployeeMapper.toResumeResponseDto(tempEmployee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Integer id) {
        service.deleteEmployeeById(id);
        return ResponseEntity.status(204).build();
    }
}
