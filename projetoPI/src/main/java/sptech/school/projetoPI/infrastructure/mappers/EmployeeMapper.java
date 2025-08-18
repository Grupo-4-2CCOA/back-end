package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.infrastructure.dto.employee.EmployeeRequestDto;
import sptech.school.projetoPI.infrastructure.dto.employee.EmployeeResponseDto;
import sptech.school.projetoPI.infrastructure.dto.employee.EmployeeResumeResponseDto;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.core.domains.Employee;

public class EmployeeMapper {
    public static Employee toEntity(EmployeeRequestDto requestObject) {
        if(requestObject == null) return null;

        return Employee.builder()
                .name(requestObject.getName())
                .cpf(requestObject.getCpf())
                .email(requestObject.getEmail())
                .password(requestObject.getPassword())
                .cep(requestObject.getCep())
                .phone(requestObject.getPhone())
                .active(true)
                .role(Role.builder().id(requestObject.getRole()).build())
                .build();
    }

    public static EmployeeResponseDto toResponseDto(Employee entity) {
        if(entity == null) return null;

        return EmployeeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cpf(entity.getCpf())
                .email(entity.getEmail())
                .cep(entity.getCep())
                .phone(entity.getPhone())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .role(RoleMapper.toResumeResponseDto(entity.getRole()))
                .build();
    }

    public static EmployeeResumeResponseDto toResumeResponseDto(Employee entity) {
        if(entity == null) return null;

        return EmployeeResumeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}