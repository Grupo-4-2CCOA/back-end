package sptech.school.projetoPI.dto.user;

import sptech.school.projetoPI.entities.User;
import sptech.school.projetoPI.enums.Role;
import sptech.school.projetoPI.exceptions.exceptionClass.EnumDoesntExistsException;

public class UserMapper {
    public static User toEntity(UserRequestDto requestObject) {
        User user = new User();

        user.setName(requestObject.getName());
        user.setCpf(requestObject.getCpf());
        user.setEmail(requestObject.getEmail());
        user.setPassword(requestObject.getPassword());
        user.setCep(requestObject.getCep());
        user.setPhone(requestObject.getPhone());
        user.setActive(true);
        user.setRole(Role.roleCheck(requestObject.getRole().toUpperCase()));

        return user;
    }

    public static User toEntity(UserResponseDto responseObject) {
        User user = new User();
        user.setId(responseObject.getId());
        return user;
    }

    public static UserResponseDto toResponseDto(User entity) {
        UserResponseDto user = new UserResponseDto();

        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setCpf(entity.getCpf());
        user.setEmail(entity.getEmail());
        user.setCep(entity.getCep());
        user.setPhone(entity.getPhone());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());
        user.setRole(entity.getRole());

        return user;
    }
}