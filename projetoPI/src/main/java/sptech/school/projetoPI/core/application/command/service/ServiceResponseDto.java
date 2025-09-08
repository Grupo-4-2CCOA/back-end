package sptech.school.projetoPI.core.application.command.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.core.application.command.category.CategoryResumeResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private Double basePrice;
    private Integer baseDuration;
    private String description;
    private String image;
    private CategoryResumeResponseDto category;
}
