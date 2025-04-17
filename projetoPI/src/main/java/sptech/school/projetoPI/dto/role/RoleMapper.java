package sptech.school.projetoPI.dto.role;

import sptech.school.projetoPI.entities.Role;

public class RoleMapper {
    public static Role toEntity(RoleRequestDto requestObject) {
        if(requestObject == null) return null;

        return Role.builder()
                .name(requestObject.getName().toUpperCase())
                .description(requestObject.getDescription())
                .active(true)
                .build();
    }

    public static RoleResponseDto toResponseDto(Role entity) {
        if(entity == null) return null;

        return RoleResponseDto.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public static RoleResumeResponseDto toResumeResponseDto(Role entity) {
        if(entity == null) return null;

        return RoleResumeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
