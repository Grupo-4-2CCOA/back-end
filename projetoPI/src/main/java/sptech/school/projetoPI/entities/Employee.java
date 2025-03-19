package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import sptech.school.projetoPI.enums.EmployeeRole;

import java.time.LocalDate;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotBlank(message = "Preencha o e-mail")
    private String email;

    @NotBlank(message = "Insira uma senha")
    @Size(min=8, message = "Sua senha deve conter, no mínimo, 8 caracteres")
    private String password;

    @Column(unique = true)
    @NotBlank(message = "Preencha o número de telefone")
    @Size(min=11, max=11, message = "O número de telefone deve conter 11 dígitos")
    private String phone;

    @Column(unique = true)
    @NotBlank(message = "Preencha o CPF")
    @Size(min=11, max=11, message = "O CPF deve conter 11 dígitos")
    private String cpf;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Preencha o cargo")
    private EmployeeRole role;

    @NotBlank(message = "Preencha o CEP")
    @Size(min=8, max=8, message = "O CEP deve conter 8 dígitos")
    private String cep;

    @PastOrPresent(message = "A data de nascimento não pode estar no futuro")
    private LocalDate birth;

    @NotBlank(message = "Preencha o nome")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
