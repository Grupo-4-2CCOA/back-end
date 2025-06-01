package sptech.school.projetoPI.dto.scheduleItem;

import sptech.school.projetoPI.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.dto.service.ServiceResumeResponseDto;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.entities.ScheduleItem;
import sptech.school.projetoPI.entities.Service;

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
