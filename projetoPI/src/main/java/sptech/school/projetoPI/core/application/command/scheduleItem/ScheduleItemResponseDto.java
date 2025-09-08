package sptech.school.projetoPI.core.application.command.scheduleItem;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.core.application.command.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.core.application.command.service.ServiceResumeResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleItemResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double finalPrice;
    private Double discount;
    private ScheduleResumeResponseDto schedule;
    private ServiceResumeResponseDto service;
}
