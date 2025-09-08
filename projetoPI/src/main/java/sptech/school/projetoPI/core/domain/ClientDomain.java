package sptech.school.projetoPI.core.domain;

import java.time.LocalDateTime;

public class ClientDomain extends UserDomain {
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
