package sptech.school.projetoPI.infrastructure.mappers;

import sptech.school.projetoPI.core.domains.Employee;
import sptech.school.projetoPI.infrastructure.dto.login.UserTokenDto;

public class UserMapper {
    public static UserTokenDto of(Client usuario, String token) {
        UserTokenDto dto = new UserTokenDto();
        dto.setUserId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNome(usuario.getName());
        dto.setToken(token);
        dto.setTipoUsuario("CLIENTE");
        return dto;
    }

    public static UserTokenDto of(Employee usuario, String token) {
        UserTokenDto dto = new UserTokenDto();
        dto.setUserId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNome(usuario.getName());
        dto.setToken(token);
        dto.setTipoUsuario("FUNC");
        return dto;
    }
}
