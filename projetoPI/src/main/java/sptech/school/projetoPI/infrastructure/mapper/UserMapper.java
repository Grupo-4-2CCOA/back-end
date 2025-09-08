package sptech.school.projetoPI.infrastructure.mapper;

import sptech.school.projetoPI.core.domain.ClientDomain;
import sptech.school.projetoPI.core.domain.EmployeeDomain;
import sptech.school.projetoPI.core.application.command.login.UserTokenDto;

public class UserMapper {
    public static UserTokenDto of(ClientDomain usuario, String token) {
        UserTokenDto dto = new UserTokenDto();
        dto.setUserId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNome(usuario.getName());
        dto.setToken(token);
        dto.setTipoUsuario("CLIENTE");
        return dto;
    }

    public static UserTokenDto of(EmployeeDomain usuario, String token) {
        UserTokenDto dto = new UserTokenDto();
        dto.setUserId(usuario.getId());
        dto.setEmail(usuario.getEmail());
        dto.setNome(usuario.getName());
        dto.setToken(token);
        dto.setTipoUsuario("FUNC");
        return dto;
    }
}
