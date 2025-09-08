package sptech.school.projetoPI.infrastructure.persistence.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
public abstract class UserJpaEntity {

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
}