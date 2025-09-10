package sptech.school.projetoPI.core.application.dto.scheduleItem;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.core.application.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.core.application.dto.service.ServiceResumeResponseDto;

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
