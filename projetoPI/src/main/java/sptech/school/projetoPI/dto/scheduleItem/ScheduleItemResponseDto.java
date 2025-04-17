package sptech.school.projetoPI.dto.scheduleItem;

import lombok.*;
import sptech.school.projetoPI.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.dto.service.ServiceResumeResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleItemResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double finalPrice;
    private Double discount;
    private ScheduleResumeResponseDto schedule;
    private ServiceResumeResponseDto service;
}
