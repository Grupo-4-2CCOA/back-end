package sptech.school.projetoPI.dto.service;

import sptech.school.projetoPI.entities.Service;

public class ServiceMapper {
    public static Service toEntity(ServiceRequestDto requestObject) {
        Service service = new Service();

        service.setType(requestObject.getType());
        service.setDescription(requestObject.getDescription());
        service.setPrice(requestObject.getPrice());
        service.setDiscount(requestObject.getDiscount());
        service.setImage(requestObject.getImage());

        return service;
    }

    public static ServiceResponseDto toResponseDto(Service entity) {
        ServiceResponseDto service = new ServiceResponseDto();

        service.setId(entity.getId());
        service.setType(entity.getType());
        service.setDescription(entity.getDescription());
        service.setPrice(entity.getPrice());
        service.setDiscount(entity.getDiscount());
        service.setImage(entity.getImage());
        service.setCreatedAt(entity.getCreatedAt());
        service.setUpdatedAt(entity.getUpdatedAt());

        return service;
    }
}
