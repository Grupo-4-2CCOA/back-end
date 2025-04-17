package sptech.school.projetoPI.dto.service;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResumeResponseDto {
    private Integer id;
    private String name;
    private Double basePrice;
}
