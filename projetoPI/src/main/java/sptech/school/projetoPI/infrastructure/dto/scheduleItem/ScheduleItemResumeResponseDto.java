package sptech.school.projetoPI.infrastructure.dto.scheduleItem;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.infrastructure.dto.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.infrastructure.dto.service.ServiceResumeResponseDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleItemResumeResponseDto {
    private Integer id;
    private Double finalPrice;
    private ScheduleResumeResponseDto schedule;
    private ServiceResumeResponseDto service;
}
