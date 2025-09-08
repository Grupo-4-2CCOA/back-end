package sptech.school.projetoPI.infrastructure.mappers;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.domains.Schedule;
import sptech.school.projetoPI.infrastructure.dto.client.ClientRequestDto;
import sptech.school.projetoPI.infrastructure.dto.client.ClientResponseDto;
import sptech.school.projetoPI.infrastructure.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.infrastructure.persistence.ClientJpaEntity;

public class ClientMapper {

    // Mapeamento de DTO para Domínio (usado no Controller)
    public static Client toDomain(ClientRequestDto requestObject) {
        if(requestObject == null) return null;

        Client client = new Client();
        client.setName(requestObject.getName());
        client.setCpf(requestObject.getCpf());
        client.setEmail(requestObject.getEmail());
        client.setPassword(requestObject.getPassword());
        client.setCep(requestObject.getCep());
        client.setPhone(requestObject.getPhone());
        client.setActive(true);
        return client;
    }

    public static Client toDomain(Integer id) {
        if (id == null) return null;

        Client client = new Client();
        client.setId(id);
        return client;
    }

    // Mapeamento de Domínio para DTO (usado no Controller)
    public static ClientResponseDto toResponseDto(Client entity) {
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

    public static ClientResumeResponseDto toResumeResponseDto(Client entity) {
        if(entity == null) return null;

        return ClientResumeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    // Mapeamento de Domínio para Entidade de Persistência (usado no Repositório)
    public static ClientJpaEntity toJpaEntity(Client domain) {
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
    public static Client toDomain(ClientJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;
        return new Client(
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