package sptech.school.projetoPI.dto.login;

import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.entities.User;

public class UserMapper {
    public static UserTokenDto of(Client usuario, String token) {
        UserTokenDto dto = new UserTokenDto();
        dto.setUserId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNome(usuario.getName());
        dto.setToken(token);
        return dto;
    }

    public static UserTokenDto of(Employee usuario, String token) {
        UserTokenDto dto = new UserTokenDto();
        dto.setUserId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNome(usuario.getName());
        dto.setToken(token);
        return dto;
    }
}
