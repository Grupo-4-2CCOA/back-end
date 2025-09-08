package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.infrastructure.dto.employee.EmployeeRequestDto;
import sptech.school.projetoPI.infrastructure.dto.employee.EmployeeResponseDto;
import sptech.school.projetoPI.infrastructure.dto.employee.EmployeeResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.EmployeeJpaEntity;

public class EmployeeMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static Employee toDomain(EmployeeRequestDto request) {
        if (request == null) return null;

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setCpf(request.getCpf());
        employee.setEmail(request.getEmail());
        employee.setPassword(request.getPassword());
        employee.setCep(request.getCep());
        employee.setPhone(request.getPhone());
        employee.setActive(true);
        // ✨ CORREÇÃO AQUI: Use o RoleMapper para converter
        employee.setRole(RoleMapper.toDomain(request.getRole()));

        return employee;
    }

    public static Employee toDomain(Integer id) {
        if (id == null) {
            return null;
        }

        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }

    /* ========= DOMAIN -> DTO (Response completo) ========= */
    public static EmployeeResponseDto toResponseDto(Employee domain) {
        if (domain == null) return null;

        return EmployeeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .cpf(domain.getCpf())
                .email(domain.getEmail())
                .cep(domain.getCep())
                .phone(domain.getPhone())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .role(RoleMapper.toResumeResponseDto(domain.getRole()))
                .build();
    }

    /* ========= DOMAIN -> DTO (Response resumido) ========= */
    public static EmployeeResumeResponseDto toResumeResponseDto(Employee domain) {
        if (domain == null) {
            return null;
        }
        return EmployeeResumeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .build();
    }

    /* ========= DOMAIN -> JPA ========= */
    public static EmployeeJpaEntity toJpaEntity(Employee domain) {
        if (domain == null) return null;

        return EmployeeJpaEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .cpf(domain.getCpf())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .cep(domain.getCep())
                .phone(domain.getPhone())
                .active(true)
                .role(RoleMapper.toJpaEntity(domain.getRole()))
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    /* ========= JPA -> DOMAIN ========= */
    public static Employee toDomain(EmployeeJpaEntity jpa) {
        if (jpa == null) return null;

        Employee domain = new Employee(RoleMapper.toDomain(jpa.getRole()));
        domain.setId(jpa.getId());
        domain.setName(jpa.getName());
        domain.setCpf(jpa.getCpf());
        domain.setEmail(jpa.getEmail());
        domain.setPassword(jpa.getPassword());
        domain.setCep(jpa.getCep());
        domain.setPhone(jpa.getPhone());
        domain.setActive(jpa.getActive());
        domain.setCreatedAt(jpa.getCreatedAt());
        domain.setUpdatedAt(jpa.getUpdatedAt());

        return domain;
    }

    /* ========= Referência simples ========= */
    public static Employee ref(Integer id) {
        if (id == null) return null;
        Employee e = new Employee((Role) null);
        e.setId(id);
        return e;
    }
}
