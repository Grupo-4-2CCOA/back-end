package sptech.school.projetoPI.infrastructure.mapper;

import sptech.school.projetoPI.core.domain.ClientDomain;
import sptech.school.projetoPI.core.application.command.client.ClientRequestDto;
import sptech.school.projetoPI.core.application.command.client.ClientResponseDto;
import sptech.school.projetoPI.core.application.command.client.ClientResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.entity.ClientJpaEntity;

public class ClientMapper {

    // Mapeamento de DTO para Domínio (usado no Controller)
    public static ClientDomain toDomain(ClientRequestDto requestObject) {
        if(requestObject == null) return null;

        ClientDomain clientDomain = new ClientDomain();
        clientDomain.setName(requestObject.getName());
        clientDomain.setCpf(requestObject.getCpf());
        clientDomain.setEmail(requestObject.getEmail());
        clientDomain.setPassword(requestObject.getPassword());
        clientDomain.setCep(requestObject.getCep());
        clientDomain.setPhone(requestObject.getPhone());
        clientDomain.setActive(true);
        return clientDomain;
    }

    public static ClientDomain toDomain(Integer id) {
        if (id == null) return null;

        ClientDomain clientDomain = new ClientDomain();
        clientDomain.setId(id);
        return clientDomain;
    }

    // Mapeamento de Domínio para DTO (usado no Controller)
    public static ClientResponseDto toResponseDto(ClientDomain entity) {
        if(entity == null) return null;

        return ClientResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cpf(entity.getCpf())
                .email(entity.getEmail())
                .cep(entity.getCep())
                .phone(entity.getPhone())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public static ClientResumeResponseDto toResumeResponseDto(ClientDomain entity) {
        if(entity == null) return null;

        return ClientResumeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    // Mapeamento de Domínio para Entidade de Persistência (usado no Repositório)
    public static ClientJpaEntity toJpaEntity(ClientDomain domain) {
        if (domain == null) return null;
        ClientJpaEntity jpaEntity = new ClientJpaEntity();
        jpaEntity.setId(domain.getId());
        jpaEntity.setActive(domain.getActive());
        jpaEntity.setCreatedAt(domain.getCreatedAt());
        jpaEntity.setUpdatedAt(domain.getUpdatedAt());
        jpaEntity.setName(domain.getName());
        jpaEntity.setCpf(domain.getCpf());
        jpaEntity.setEmail(domain.getEmail());
        jpaEntity.setPassword(domain.getPassword());
        jpaEntity.setPhone(domain.getPhone());
        jpaEntity.setCep(domain.getCep());
        return jpaEntity;
    }

    // Mapeamento de Entidade de Persistência para Domínio (usado no Repositório)
    public static ClientDomain toDomain(ClientJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        return new ClientDomain(
                jpaEntity.getId(),
                jpaEntity.getActive(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getUpdatedAt(),
                jpaEntity.getName(),
                jpaEntity.getCpf(),
                jpaEntity.getEmail(),
                jpaEntity.getPassword(),
                jpaEntity.getPhone(),
                jpaEntity.getCep()
        );
    }
}