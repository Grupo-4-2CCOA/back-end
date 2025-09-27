package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
public class RoleJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "is_active", nullable = false)
  private Boolean active;
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  @Column(nullable = false, unique = true)
  private String name;
  private String description;
  // mappedBy é o campo da classe UserJpaEntity
  // cascade diz se operações de repository.save(roleJpaEntity), também vão salvar/atualizar os usuários associados.
  @OneToMany(mappedBy = "roleJpaEntity", cascade = CascadeType.ALL)
  private Set<UserJpaEntity> userJpaEntities;
}
