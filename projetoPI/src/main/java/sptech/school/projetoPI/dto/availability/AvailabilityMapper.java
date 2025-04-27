package sptech.school.projetoPI.dto.availability;

import sptech.school.projetoPI.dto.employee.EmployeeMapper;
import sptech.school.projetoPI.entities.Availability;
import sptech.school.projetoPI.entities.Employee;

public class AvailabilityMapper {
    public static Availability toEntity(AvailabilityRequestDto requestObject) {
        if(requestObject == null) return null;

        return Availability.builder()
                .isAvailable(true)
                .day(requestObject.getDay())
                .startTime(requestObject.getStartTime())
                .endTime(requestObject.getEndTime())
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