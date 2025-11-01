package sptech.school.projetoPI.infrastructure.mappers;

import org.springframework.stereotype.Component;
import sptech.school.projetoPI.core.application.dto.service.ServiceResponseDto;
import sptech.school.projetoPI.core.domains.ServiceDomain;
import sptech.school.projetoPI.core.gateways.FileUploadGateway;

@Component
public class ServiceMapperImage {

    private final FileUploadGateway fileUploadGateway;

    public ServiceMapperImage(FileUploadGateway fileUploadGateway) {
        this.fileUploadGateway = fileUploadGateway;
    }

    public ServiceResponseDto toResponseDtoWithImageUrl(ServiceDomain domain) {
        if(domain == null) return null;

        return ServiceResponseDto.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .basePrice(domain.getBasePrice())
                .baseDuration(domain.getBaseDuration())
                .image(domain.getImage() != null ? fileUploadGateway.getFileUrl(domain.getImage()) : null)
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .category(CategoryMapper.toResumeResponseDto(domain.getCategory()))
                .build();
    }
}
