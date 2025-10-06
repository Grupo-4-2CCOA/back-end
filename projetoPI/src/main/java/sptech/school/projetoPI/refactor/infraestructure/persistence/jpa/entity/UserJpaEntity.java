package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

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
  private Boolean isActive;
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
  @Column(name = "updated_at")
  private Instant updatedAt;
  @Column(nullable = false)
  private String name;
  @Column(unique = true)
  private String cpf;
  @Column(nullable = false, unique = true)
  private String email;
  private String phone;
  private String cep;
  // fetch define quando o objeto (nesse caso, o objeto de Role) vai ser carregado do banco de dados para a memória,
  // no caso do LAZY, ele só é carregado em memória quando alguém acessa userJpaEntity.roleJpaEntity,
  // no caso do EAGER, ele é carregado em memória quando o userJpaEntity for carregado em memória, então ele pré-carrega os dados
  @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn é o @Column, mas pra FKs
  @JoinColumn(name = "fk_role", nullable = false)
  private RoleJpaEntity roleJpaEntity;
}
