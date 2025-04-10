package sptech.school.projetoPI.dto.user;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

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
    @NotBlank(message = "Insira um CPF")
    private String cpf;

    @Size(max = 11, min = 11, message = "Telefone deve conter 11 dígitos")
    @NotBlank(message = "Insira um número de telefone")
    private String phone;

    @Past(message = "Data de nascimento inválido")
    private LocalDate birth;

    @Size(min = 8, max = 8, message = "CEP inválido")
    @NotBlank(message = "Insira o CEP")
    private String cep;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
