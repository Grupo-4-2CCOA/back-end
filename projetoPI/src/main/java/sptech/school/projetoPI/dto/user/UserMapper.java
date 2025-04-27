package sptech.school.projetoPI.dto.user;

import sptech.school.projetoPI.dto.role.RoleMapper;
import sptech.school.projetoPI.entities.Role;
import sptech.school.projetoPI.entities.User;

public class UserMapper {
    public static User toEntity(UserRequestDto requestObject) {
        if(requestObject == null) return null;

        return User.builder()
                .name(requestObject.getName())
                .cpf(requestObject.getCpf())
                .email(requestObject.getEmail())
                .password(requestObject.getPassword())
                .cep(requestObject.getCep())
                .phone(requestObject.getPhone())
                .active(true)
                .build();
    }

    public static UserResponseDto toResponseDto(User entity) {
        if(entity == null) return null;

        return UserResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cpf(entity.getCpf())
                .email(entity.getEmail())
                .cep(entity.getCep())
                .phone(entity.getPhone())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .role(RoleMapper.toResumeResponseDto(entity.getRole()))
                .build();
    }

    public static UserResumeResponseDto toResumeResponseDto(User entity) {
        if(entity == null) return null;

        return UserResumeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static User of(UserLoginDto userLoginDto){
        if(userLoginDto == null){
            return null;
        }

        User user = new User();

        user.setEmail(userLoginDto.getEmail());
        user.setPassword(userLoginDto.getPassword());

        return user;
    }

    public static UserTokenDto of(User user, String token){
        UserTokenDto userTokenDto = new UserTokenDto();

        userTokenDto.setUserId(user.getId());
        userTokenDto.setEmail(user.getEmail());
        userTokenDto.setNome(user.getName());
        userTokenDto.setToken(token);

        return userTokenDto;
    }
}