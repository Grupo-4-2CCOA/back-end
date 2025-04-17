package sptech.school.projetoPI.dto.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "Insira o cargo do usuário")
    private String role;

    @Size(max = 80, message = "Nome muito longo")
    @NotBlank(message = "Insira o nome")
    private String name;

    @Size(max = 80, message = "E-mail muito longo")
    @Email(message = "Valor inserido não é um e-mail")
    @NotBlank(message = "Insira um e-mail")
    private String email;

    @Size(min = 8, max = 80, message = "Senha deve ter entre 8 e 80 caracteres")
    @NotBlank(message = "Insira uma senha")
    private String password;

    @CPF(message = "CPF inválido")
    private String cpf;

    @Size(max = 11, min = 11, message = "Telefone deve conter 11 dígitos")
    private String phone;

    @Size(min = 8, max = 8, message = "CEP inválido")
    private String cep;
}
