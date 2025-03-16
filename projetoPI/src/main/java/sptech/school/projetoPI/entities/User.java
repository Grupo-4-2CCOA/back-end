package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import sptech.school.projetoPI.enums.UserFidelity;

import java.time.LocalDate;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @NotBlank
    private String email;

    @NotBlank
    @Size(min=8)
    private String password;

    @Column(unique = true)
    @NotBlank
    @Size(min=11, max=11)
    private String phone;

    @Column(unique = true)
    @NotBlank
    @Size(min=11, max=11)
    private String cpf;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min=8, max=8)
    private String cep;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserFidelity fidelity;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UserFidelity getFidelity() {
        return fidelity;
    }

    public void setFidelity(UserFidelity fidelity) {
        this.fidelity = fidelity;
    }
}
