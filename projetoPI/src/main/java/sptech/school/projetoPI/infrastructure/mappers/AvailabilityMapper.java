package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityRequestDto;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityResponseDto;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.AvailabilityJpaEntity;
import sptech.school.projetoPI.infrastructure.dto.employee.EmployeeResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.EmployeeJpaEntity;

public class AvailabilityMapper {

    // Método para converter do DTO de requisição para o objeto de DOMÍNIO
    public static Availability toDomain(AvailabilityRequestDto requestObject) {
        if (requestObject == null) {
            return null;
        }

        Employee employee = (requestObject.getEmployee() != null) ?
                EmployeeMapper.toDomain(requestObject.getEmployee()) : null;

        Availability availability = new Availability();
        availability.setDay(requestObject.getDay());
        availability.setStartTime(requestObject.getStartTime());
        availability.setEndTime(requestObject.getEndTime());
        availability.setEmployee(employee);

        return availability;
    }

    public static AvailabilityResponseDto toResponseDto(Availability domain) {
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
    public static AvailabilityJpaEntity toJpaEntity(Availability domain) {
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
    public static Availability toDomain(AvailabilityJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        // Mapeia o Employee da entidade JPA para o domínio
        Employee employeeDomain = (jpaEntity.getEmployee() != null) ?
                EmployeeMapper.toDomain(jpaEntity.getEmployee()) : null;

        Availability availability = new Availability();
        availability.setId(jpaEntity.getId());
        availability.setAvailable(jpaEntity.getIsAvailable());
        availability.setDay(jpaEntity.getDay());
        availability.setStartTime(jpaEntity.getStartTime());
        availability.setEndTime(jpaEntity.getEndTime());
        availability.setEmployee(employeeDomain);
        availability.setCreatedAt(jpaEntity.getCreatedAt());
        availability.setUpdatedAt(jpaEntity.getUpdatedAt());
        return availability;
    }

    // Método para converter do objeto de DOMÍNIO para o DTO de resposta resumido
    public static AvailabilityResumeResponseDto toResumeResponseDto(Availability domain) {
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