package sptech.school.projetoPI.dto.service;

import lombok.Getter;
import lombok.Setter;
import sptech.school.projetoPI.dto.category.CategoryResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class ServiceResponseDto {
    private Integer Id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private Double basePrice;
    private Integer baseDuration;
    private String description;
    private String image;
    private CategoryResponseDto category;
}
