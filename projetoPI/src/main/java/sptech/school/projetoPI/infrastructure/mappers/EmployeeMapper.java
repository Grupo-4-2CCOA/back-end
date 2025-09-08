package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.EmployeeDomain;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.application.command.employee.EmployeeRequestDto;
import sptech.school.projetoPI.core.application.command.employee.EmployeeResponseDto;
import sptech.school.projetoPI.core.application.command.employee.EmployeeResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.EmployeeJpaEntity;

public class EmployeeMapper {

    /* ========= DTO -> DOMAIN ========= */
    public static EmployeeDomain toDomain(EmployeeRequestDto request) {
        if (request == null) return null;

        EmployeeDomain employeeDomain = new EmployeeDomain();
        employeeDomain.setName(request.getName());
        employeeDomain.setCpf(request.getCpf());
        employeeDomain.setEmail(request.getEmail());
        employeeDomain.setPassword(request.getPassword());
        employeeDomain.setCep(request.getCep());
        employeeDomain.setPhone(request.getPhone());
        employeeDomain.setActive(true);
        // ✨ CORREÇÃO AQUI: Use o RoleMapper para converter
        employeeDomain.setRole(RoleMapper.toDomain(request.getRole()));

        return employeeDomain;
    }

    public static EmployeeDomain toDomain(Integer id) {
        if (id == null) {
            return null;
        }

        EmployeeDomain employeeDomain = new EmployeeDomain();
        employeeDomain.setId(id);
        return employeeDomain;
    }

    /* ========= DOMAIN -> DTO (Response completo) ========= */
    public static EmployeeResponseDto toResponseDto(EmployeeDomain domain) {
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
    public static EmployeeResumeResponseDto toResumeResponseDto(EmployeeDomain domain) {
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
    public static EmployeeJpaEntity toJpaEntity(EmployeeDomain domain) {
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
    public static EmployeeDomain toDomain(EmployeeJpaEntity jpa) {
        if (jpa == null) return null;

        EmployeeDomain domain = new EmployeeDomain(RoleMapper.toDomain(jpa.getRole()));
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
    public static EmployeeDomain ref(Integer id) {
        if (id == null) return null;
        EmployeeDomain e = new EmployeeDomain((RoleDomain) null);
        e.setId(id);
        return e;
    }
}
