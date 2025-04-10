package sptech.school.projetoPI.dto.user;

import sptech.school.projetoPI.entities.User;

public class UserMapper {
    public static User toEntity(UserRequestDto requestObject) {
        User user = new User();

        user.setName(requestObject.getName());
        user.setCpf(requestObject.getCpf());
        user.setEmail(requestObject.getEmail());
        user.setPassword(requestObject.getPassword());
        user.setBirth(requestObject.getBirth());
        user.setCep(requestObject.getCep());
        user.setPhone(requestObject.getPhone());

        return user;
    }

    public static UserResponseDto toResponseDto(User entity) {
        UserResponseDto user = new UserResponseDto();

        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setCpf(entity.getCpf());
        user.setEmail(entity.getEmail());
        user.setCep(entity.getCep());
        user.setBirth(entity.getBirth());
        user.setPhone(entity.getPhone());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());

        return user;
    }
}