package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.application.dto.service.ServiceRequestDto;
import sptech.school.projetoPI.core.application.dto.service.ServiceResponseDto;
import sptech.school.projetoPI.core.application.dto.service.ServiceResumeResponseDto;
import sptech.school.projetoPI.core.gateways.FileUploadGateway;
import sptech.school.projetoPI.infrastructure.persistence.entity.CategoryJpaEntity;
import sptech.school.projetoPI.infrastructure.persistence.entity.ServiceJpaEntity;

public class ServiceMapper {

    private FileUploadGateway fileUploadGateway;

    public ServiceMapper(FileUploadGateway fileUploadGateway) {
        this.fileUploadGateway = fileUploadGateway;
    }

    /* ========= DTO -> DOMAIN (Controller to Use Case) ========= */
    public static ServiceDomain toDomain(ServiceRequestDto requestObject) {
        if(requestObject == null) return null;

        ServiceDomain service = new ServiceDomain();
        service.setName(requestObject.getName());
        service.setDescription(requestObject.getDescription());
        service.setBasePrice(requestObject.getBasePrice());
        service.setBaseDuration(requestObject.getBaseDuration());
        service.setImage(requestObject.getImage());
        service.setActive(true);

        // Delegação de mapeamento de entidade relacionada
        service.setCategory(CategoryMapper.toDomain(requestObject.getCategory().getId()));

        return service;
    }

    public static ServiceDomain toDomain(Integer id) {
        if (id == null) return null;

        ServiceDomain service = new ServiceDomain();
        service.setId(id);
        return service;
    }

    /* ========= DOMAIN -> DTO (Full Response) ========= */
    public static ServiceResponseDto toResponseDto(ServiceDomain domain) {
        if(domain == null) return null;

        return ServiceResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .basePrice(domain.getBasePrice())
                .baseDuration(domain.getBaseDuration())
                .image(domain.getImage())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .category(CategoryMapper.toResumeResponseDto(domain.getCategory()))
                .build();
    }

    /* ========= DOMAIN -> DTO (Resume Response) ========= */
    public static ServiceResumeResponseDto toResumeResponseDto(ServiceDomain domain) {
        if(domain == null) return null;

        return ServiceResumeResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .basePrice(domain.getBasePrice())
                .build();
    }

    //---------------------------------------------------------

    /* ========= DOMAIN -> JPA (Use Case to Repository) ========= */
    public static ServiceJpaEntity toJpaEntity(ServiceDomain domain) {
        if (domain == null) return null;

        ServiceJpaEntity jpaEntity = new ServiceJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setName(domain.getName());
        jpaEntity.setDescription(domain.getDescription());
        jpaEntity.setBasePrice(domain.getBasePrice());
        jpaEntity.setBaseDuration(domain.getBaseDuration());
        jpaEntity.setImage(domain.getImage());
        jpaEntity.setActive(domain.getActive());
        jpaEntity.setCreatedAt(domain.getCreatedAt());
        jpaEntity.setUpdatedAt(domain.getUpdatedAt());

        if (domain.getCategory() != null && domain.getCategory().getId() != null) {
            CategoryJpaEntity categoryJpa = new CategoryJpaEntity();
            categoryJpa.setId(domain.getCategory().getId());
            jpaEntity.setCategory(categoryJpa);
        } else {
            throw new IllegalArgumentException("Categoria não pode ser nula ou sem ID");
        }
        return jpaEntity;
    }

    /* ========= JPA -> DOMAIN (Repository to Use Case) ========= */
    public static ServiceDomain toDomain(ServiceJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        ServiceDomain domain = new ServiceDomain();
        domain.setId(jpaEntity.getId());
        domain.setName(jpaEntity.getName());
        domain.setDescription(jpaEntity.getDescription());
        domain.setBasePrice(jpaEntity.getBasePrice());
        domain.setBaseDuration(jpaEntity.getBaseDuration());
        domain.setImage(jpaEntity.getImage());
        domain.setActive(jpaEntity.getActive());
        domain.setCreatedAt(jpaEntity.getCreatedAt());
        domain.setUpdatedAt(jpaEntity.getUpdatedAt());

        domain.setCategory(CategoryMapper.toDomain(jpaEntity.getCategory().getId()));

        return domain;
    }
}