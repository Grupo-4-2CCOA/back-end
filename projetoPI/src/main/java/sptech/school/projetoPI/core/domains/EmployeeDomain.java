package sptech.school.projetoPI.core.domains;

import java.time.LocalDateTime;

public class EmployeeDomain extends User {
    private RoleDomain roleDomain;

    public EmployeeDomain(
            Integer id,
            Boolean active,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String name,
            String cpf,
            String email,
            String password,
            String phone,
            String cep
    ) {
        super(id, active, createdAt, updatedAt, name, cpf, email, password, phone, cep);
    }

    public EmployeeDomain(RoleDomain roleDomain) {
        this.roleDomain = roleDomain;
    }

    public EmployeeDomain() {}

    public RoleDomain getRole() {
        return roleDomain;
    }

    public void setRole(RoleDomain roleDomain) {
        this.roleDomain = roleDomain;
    }
}
