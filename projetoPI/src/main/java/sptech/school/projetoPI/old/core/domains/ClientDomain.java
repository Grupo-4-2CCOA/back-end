package sptech.school.projetoPI.old.core.domains;

import java.time.LocalDateTime;

public class ClientDomain extends User {
    public ClientDomain(
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

    public ClientDomain() {}
}
