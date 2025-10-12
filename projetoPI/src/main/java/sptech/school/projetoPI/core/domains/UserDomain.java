package sptech.school.projetoPI.core.domains;

import java.time.LocalDateTime;

public class UserDomain {
    private Integer id;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private String cep;
    private RoleDomain roleDomain;

    public UserDomain(Integer id, Boolean active, LocalDateTime createdAt, LocalDateTime updatedAt, String name, String cpf, String email, String phone, String cep, RoleDomain roleDomain) {
        this.id = id;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.cep = cep;
        this.roleDomain = roleDomain;
    }

    public UserDomain() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
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
