package sptech.school.projetoPI.core.domains;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class Employee extends User {
    private Role role;

    public Employee(
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

    public Employee(Role role) {
        this.role = role;
    }

    public Employee() {}

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
