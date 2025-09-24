package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.core.domain.field.Cep;
import sptech.school.projetoPI.refactor.core.domain.field.Cpf;
import sptech.school.projetoPI.refactor.core.domain.field.Email;
import sptech.school.projetoPI.refactor.core.domain.field.Phone;

import java.time.LocalDateTime;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private String phone;
    private String cep;
    private RoleEntity roleEntity;
}
