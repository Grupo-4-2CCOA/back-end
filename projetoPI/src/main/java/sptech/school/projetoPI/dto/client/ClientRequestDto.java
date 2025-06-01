package sptech.school.projetoPI.dto.client;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDto {
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

    @NotBlank(message = "Insira o CPF")
    @CPF(message = "CPF inválido")
    private String cpf;

    @Size(max = 11, min = 11, message = "Telefone deve conter 11 dígitos")
    private String phone;

    @Size(min = 8, max = 8, message = "CEP inválido")
    private String cep;
}
