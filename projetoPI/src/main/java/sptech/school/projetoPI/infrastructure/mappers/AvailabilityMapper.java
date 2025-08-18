package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.Availability;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityRequestDto;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityResponseDto;
import sptech.school.projetoPI.infrastructure.dto.availability.AvailabilityResumeResponseDto;
import sptech.school.projetoPI.core.domains.Employee;

public class AvailabilityMapper {

    public static Availability toDomain(AvailabilityRequestDto requestObject) {
        if(requestObject == null) return null;

        return Availability.builder()
                .isAvailable(true)
                .day(requestObject.getDay())
                .startTime(requestObject.getStartTime())
                .endTime(requestObject.getEndTime())
                // Aqui criamos um objeto Employee com o ID, mas sem todos os dados
                .employee(Employee.builder().id(requestObject.getEmployee()).build())
                .build();
    }

    public static AvailabilityResponseDto toResponseDto(Availability entity) {
        if(entity == null) return null;

        return AvailabilityResponseDto.builder()
                .id(entity.getId())
                .day(entity.getDay())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                // Adicionado: Mapeamento seguro do Employee para o DTO
                // O método 'toResumeResponseDto' do 'EmployeeMapper' deve ser público e estático
                .employee(EmployeeMapper.toResumeResponseDto(entity.getEmployee()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static AvailabilityResumeResponseDto toResumeResponseDto(Availability entity) {
        if(entity == null) return null;

        return AvailabilityResumeResponseDto.builder()
                .id(entity.getId())
                .day(entity.getDay())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }
}