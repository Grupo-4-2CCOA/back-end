package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.dto.scheduleItem.ScheduleItemRequestDto;
import sptech.school.projetoPI.infrastructure.dto.scheduleItem.ScheduleItemResponseDto;
import sptech.school.projetoPI.infrastructure.dto.scheduleItem.ScheduleItemResumeResponseDto;
import sptech.school.projetoPI.infrastructure.dto.service.ServiceResumeResponseDto;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.core.domains.ScheduleItem;
import sptech.school.projetoPI.core.domains.Service;

public class ScheduleItemMapper {
    public static ScheduleItem toEntity(ScheduleItemRequestDto requestObject) {
        if(requestObject == null) return null;

        return ScheduleItem.builder()
                .finalPrice(requestObject.getFinalPrice())
                .discount(requestObject.getDiscount())
                .schedule(Schedule.builder().id(requestObject.getSchedule()).build())
                .service(Service.builder().id(requestObject.getService()).build())
                .build();
    }

    public static ScheduleItemResponseDto toResponseDto(ScheduleItem entity) {
        if(entity == null) return null;

        return ScheduleItemResponseDto.builder()
                .id(entity.getId())
                .finalPrice(entity.getFinalPrice())
                .discount(entity.getDiscount())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .schedule(ScheduleResumeResponseDto.builder().id(entity.getId()).build())
                .service(ServiceResumeResponseDto.builder().id(entity.getId()).build())
                .build();
    }

    public static ScheduleItemResumeResponseDto toResumeResponseDto(ScheduleItem entity) {
        if(entity == null) return null;

        return ScheduleItemResumeResponseDto.builder()
                .id(entity.getId())
                .finalPrice(entity.getFinalPrice())
                .schedule(ScheduleResumeResponseDto.builder().id(entity.getId()).build())
                .service(ServiceResumeResponseDto.builder().id(entity.getId()).build())
                .build();
    }
}
