package sptech.school.projetoPI.dto.schedule;

import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.enums.Status;

public class ScheduleMapper {
    public static Schedule toEntity(ScheduleRequestDto requestObject) {
        Schedule schedule = new Schedule();

        schedule.setStatus(Status.valueOf(requestObject.getStatus()));
        schedule.setDateTime(requestObject.getDateTime());

        return schedule;
    }

    public static ScheduleResponseDto toResponseDto(Schedule entity) {
        ScheduleResponseDto schedule = new ScheduleResponseDto();

        schedule.setId(entity.getId());
        schedule.setStatus(entity.getStatus());
        schedule.setDateTime(entity.getDateTime());
        schedule.setCreatedAt(entity.getCreatedAt());
        schedule.setUpdatedAt(entity.getUpdatedAt());

        return schedule;
    }
}
