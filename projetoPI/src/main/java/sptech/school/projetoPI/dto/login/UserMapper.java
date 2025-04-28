package sptech.school.projetoPI.dto.login;

import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.entities.User;

public class UserMapper {
    public static Client of(UserLoginDto usuarioLoginDto) {
        Client usuario = new Client();

        usuario.setEmail(usuarioLoginDto.getEmail());
        usuario.setPassword(usuarioLoginDto.getSenha());

        return usuario;
    }

    public static UserTokenDto of(Client usuario, String token) {
        UserTokenDto usuarioTokenDto = new UserTokenDto();

        usuarioTokenDto.setUserId(usuario.getId());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getName());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }
}
