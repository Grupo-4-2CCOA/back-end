package sptech.school.projetoPI.core.application.dto.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResumeResponseDto {
    private Integer id;
    private String name;
    private Double basePrice;
}
