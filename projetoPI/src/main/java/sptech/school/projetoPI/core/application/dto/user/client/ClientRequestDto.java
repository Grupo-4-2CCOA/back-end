package sptech.school.projetoPI.core.application.dto.user.client;

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

    @NotBlank(message = "Insira o CPF")
    @CPF(message = "CPF inválido")
    private String cpf;

    @Size(max = 11, min = 11, message = "Telefone deve conter 11 dígitos")
    private String phone;

    @Size(min = 8, max = 8, message = "CEP inválido")
    private String cep;

    @Positive(message = "ID inválido para Cargo")
    @NotNull(message = "Insira o cargo do usuário")
    private Integer role;
}
