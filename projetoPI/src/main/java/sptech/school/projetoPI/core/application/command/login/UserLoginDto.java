package sptech.school.projetoPI.core.application.command.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    private String email;
    private String senha;
}
