package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.infrastructure.dto.service.ServiceRequestDto;
import sptech.school.projetoPI.infrastructure.dto.service.ServiceResponseDto;
import sptech.school.projetoPI.infrastructure.dto.service.ServiceResumeResponseDto;
import sptech.school.projetoPI.core.domains.Category;
import sptech.school.projetoPI.core.domains.Service;

public class ServiceMapper {
    public static Service toEntity(ServiceRequestDto requestObject) {
        if(requestObject == null) return null;

        return Service.builder()
                .name(requestObject.getName())
                .description(requestObject.getDescription())
                .basePrice(requestObject.getBasePrice())
                .baseDuration(requestObject.getBaseDuration())
                .image(requestObject.getImage())
                .active(true)
                .category(Category.builder().id(requestObject.getCategory()).build())
                .build();
    }

    public static ServiceResponseDto toResponseDto(Service entity) {
        if(entity == null) return null;

        return ServiceResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .basePrice(entity.getBasePrice())
                .baseDuration(entity.getBaseDuration())
                .image(entity.getImage())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .category(CategoryMapper.toResumeResponseDto(entity.getCategory()))
                .build();
    }

    public static ServiceResumeResponseDto toResumeResponseDto(Service entity) {
        if(entity == null) return null;

        return ServiceResumeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .basePrice(entity.getBasePrice())
                .build();
    }
}
