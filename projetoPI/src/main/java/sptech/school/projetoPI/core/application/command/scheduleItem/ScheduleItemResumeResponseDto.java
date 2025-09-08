package sptech.school.projetoPI.core.application.command.scheduleItem;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.core.application.command.schedule.ScheduleResumeResponseDto;
import sptech.school.projetoPI.core.application.command.service.ServiceResumeResponseDto;

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
