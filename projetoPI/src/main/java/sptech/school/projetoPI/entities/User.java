package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.projetoPI.enums.Role;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private String phone;
    private String cep;
}
