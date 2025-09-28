package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "is_active", nullable = false)
  private Boolean active;
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  @Column(nullable = false)
  private String name;
  @Column(unique = true)
  private String cpf;
  @Column(nullable = false, unique = true)
  private String email;
  private String phone;
  private String cep;
  // fetch define quando o objeto (nesse caso, o objeto de Role) vai ser "puxado" do banco de dados,
  // no caso do LAZY, ele só é "puxado" quando alguém acessa userJpaEntity.roleJpaEntity,
  // no caso do EAGER, ele é "puxado" quando o userJpaEntity for "puxado", então ele pré-carrega os dados
  @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn é o @Column, mas pra FKs
  @JoinColumn(name = "fk_role", nullable = false)
  private RoleJpaEntity roleJpaEntity;
}
