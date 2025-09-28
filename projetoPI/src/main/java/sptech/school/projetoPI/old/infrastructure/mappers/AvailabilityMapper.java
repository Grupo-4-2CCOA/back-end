package sptech.school.projetoPI.old.infrastructure.mappers;

import sptech.school.projetoPI.old.core.domains.AvailabilityDomain;
import sptech.school.projetoPI.old.core.domains.EmployeeDomain;
import sptech.school.projetoPI.old.core.application.dto.availability.AvailabilityRequestDto;
import sptech.school.projetoPI.old.core.application.dto.availability.AvailabilityResponseDto;
import sptech.school.projetoPI.old.core.application.dto.availability.AvailabilityResumeResponseDto;
import sptech.school.projetoPI.old.infrastructure.persistence.entity.AvailabilityJpaEntity;
import sptech.school.projetoPI.old.core.application.dto.employee.EmployeeResumeResponseDto;
import sptech.school.projetoPI.old.infrastructure.persistence.entity.EmployeeJpaEntity;

public class AvailabilityMapper {

    // Método para converter do DTO de requisição para o objeto de DOMÍNIO
    public static AvailabilityDomain toDomain(AvailabilityRequestDto requestObject) {
        if (requestObject == null) {
            return null;
        }

        EmployeeDomain employeeDomain = (requestObject.getEmployee() != null) ?
                EmployeeMapper.toDomain(requestObject.getEmployee()) : null;

        AvailabilityDomain availabilityDomain = new AvailabilityDomain();
        availabilityDomain.setDay(requestObject.getDay());
        availabilityDomain.setStartTime(requestObject.getStartTime());
        availabilityDomain.setEndTime(requestObject.getEndTime());
        availabilityDomain.setEmployee(employeeDomain);

        return availabilityDomain;
    }

    public static AvailabilityResponseDto toResponseDto(AvailabilityDomain domain) {
        if (domain == null) {
            return null;
        }

        EmployeeResumeResponseDto employeeDto = (domain.getEmployee() != null) ?
                EmployeeMapper.toResumeResponseDto(domain.getEmployee()) : null;

        return AvailabilityResponseDto.builder()
                .id(domain.getId())
                .day(domain.getDay())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .employee(employeeDto)
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    // Método para converter do DOMÍNIO para a ENTIDADE JPA
    public static AvailabilityJpaEntity toJpaEntity(AvailabilityDomain domain) {
        if (domain == null) {
            return null;
        }

        // Mapeia o Employee do domínio para a entidade JPA
        EmployeeJpaEntity employeeJpa = (domain.getEmployee() != null) ?
                EmployeeMapper.toJpaEntity(domain.getEmployee()) : null;

        return AvailabilityJpaEntity.builder()
                .id(domain.getId())
                .isAvailable(domain.getAvailable())
                .day(domain.getDay())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .employee(employeeJpa)
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    // Método para converter da ENTIDADE JPA para o DOMÍNIO
    public static AvailabilityDomain toDomain(AvailabilityJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        // Mapeia o Employee da entidade JPA para o domínio
        EmployeeDomain employeeDomain = (jpaEntity.getEmployee() != null) ?
                EmployeeMapper.toDomain(jpaEntity.getEmployee()) : null;

        AvailabilityDomain availabilityDomain = new AvailabilityDomain();
        availabilityDomain.setId(jpaEntity.getId());
        availabilityDomain.setAvailable(jpaEntity.getIsAvailable());
        availabilityDomain.setDay(jpaEntity.getDay());
        availabilityDomain.setStartTime(jpaEntity.getStartTime());
        availabilityDomain.setEndTime(jpaEntity.getEndTime());
        availabilityDomain.setEmployee(employeeDomain);
        availabilityDomain.setCreatedAt(jpaEntity.getCreatedAt());
        availabilityDomain.setUpdatedAt(jpaEntity.getUpdatedAt());
        return availabilityDomain;
    }

    // Método para converter do objeto de DOMÍNIO para o DTO de resposta resumido
    public static AvailabilityResumeResponseDto toResumeResponseDto(AvailabilityDomain domain) {
        if (domain == null) {
            return null;
        }

        return AvailabilityResumeResponseDto.builder()
                .id(domain.getId())
                .day(domain.getDay())
                .startTime(domain.getStartTime())
                .endTime(domain.getEndTime())
                .build();
    }
}