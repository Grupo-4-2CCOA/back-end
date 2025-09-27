package sptech.school.projetoPI.old.infrastructure.mappers;

import sptech.school.projetoPI.old.core.domains.ClientDomain;
import sptech.school.projetoPI.old.core.domains.EmployeeDomain;
import sptech.school.projetoPI.old.core.application.dto.login.UserTokenDto;

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
