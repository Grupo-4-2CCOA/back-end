package sptech.school.projetoPI.dto.employee;

import org.springframework.security.core.userdetails.User;
import sptech.school.projetoPI.dto.client.ClientLoginDto;
import sptech.school.projetoPI.dto.client.ClientTokenDto;
import sptech.school.projetoPI.dto.role.RoleMapper;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.entities.Role;
import sptech.school.projetoPI.entities.Employee;

public class EmployeeMapper {
    public static Employee toEntity(EmployeeRequestDto requestObject) {
        if(requestObject == null) return null;

        return Employee.builder()
                .name(requestObject.getName())
                .cpf(requestObject.getCpf())
                .email(requestObject.getEmail())
                .password(requestObject.getPassword())
                .cep(requestObject.getCep())
                .phone(requestObject.getPhone())
                .active(true)
                .build();
    }

    public static EmployeeResponseDto toResponseDto(Employee entity) {
        if(entity == null) return null;

        return EmployeeResponseDto.builder()
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

    public static EmployeeResumeResponseDto toResumeResponseDto(Employee entity) {
        if(entity == null) return null;

        return EmployeeResumeResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static Client of(ClientLoginDto userLoginDto){
        if(userLoginDto == null){
            return null;
        }

        Client user = new Client();

        user.setEmail(userLoginDto.getEmail());
        user.setPassword(userLoginDto.getSenha());

        return user;
    }

    public static ClientTokenDto of(Client user, String token){
        ClientTokenDto userTokenDto = new ClientTokenDto();

        userTokenDto.setUserId(user.getId());
        userTokenDto.setEmail(user.getEmail());
        userTokenDto.setNome(user.getName());
        userTokenDto.setToken(token);

        return userTokenDto;
    }
}