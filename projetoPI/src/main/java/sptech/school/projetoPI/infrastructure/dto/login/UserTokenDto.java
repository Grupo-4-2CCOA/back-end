package sptech.school.projetoPI.infrastructure.dto.login;

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
