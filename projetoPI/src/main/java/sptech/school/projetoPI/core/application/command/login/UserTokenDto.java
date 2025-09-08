package sptech.school.projetoPI.core.application.command.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenDto {
    private Integer userId;
    private String nome;
    private String email;
    private String token;
    private String tipoUsuario;
}
