package sptech.school.projetoPI.dto.service;

import sptech.school.projetoPI.dto.category.CategoryMapper;
import sptech.school.projetoPI.entities.Service;

public class ServiceMapper {
    public static Service toEntity(ServiceRequestDto requestObject) {
        Service service = new Service();

        service.setName(requestObject.getName());
        service.setDescription(requestObject.getDescription());
        service.setBasePrice(requestObject.getBasePrice());
        service.setBaseDuration(requestObject.getBaseDuration());
        service.setImage(requestObject.getImage());
        service.setActive(true);
        service.setCategory(CategoryMapper.toEntity(requestObject.getCategory()));

        return service;
    }

    public static ServiceResponseDto toResponseDto(Service entity) {
        ServiceResponseDto service = new ServiceResponseDto();

        service.setId(entity.getId());
        service.setName(entity.getName());
        service.setDescription(entity.getDescription());
        service.setBasePrice(entity.getBasePrice());
        service.setBaseDuration(entity.getBaseDuration());
        service.setImage(entity.getImage());
        service.setCreatedAt(entity.getCreatedAt());
        service.setUpdatedAt(entity.getUpdatedAt());
        service.setCategory(CategoryMapper.toResponseDto(entity.getCategory()));

        return service;
    }
}
