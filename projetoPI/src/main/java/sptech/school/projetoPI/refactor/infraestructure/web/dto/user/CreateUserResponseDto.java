package sptech.school.projetoPI.refactor.infraestructure.web.dto.user;


import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;

public class CreateUserResponseDto {
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private String cep;
    private RoleDomain roleDomain;

    public CreateUserResponseDto(String name, String cpf, String email, String phone, String cep, RoleDomain roleDomain) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.cep = cep;
        this.roleDomain = roleDomain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public RoleDomain getRoleDomain() {
        return roleDomain;
    }

    public void setRoleDomain(RoleDomain roleDomain) {
        this.roleDomain = roleDomain;
    }
}
