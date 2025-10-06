package sptech.school.projetoPI.refactor.infraestructure.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.time.Instant;

@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// anottation para não enviar campos com valores nulos no INSERT do banco de dados:
@DynamicInsert
public class RoleJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
  @Column(name = "is_active", nullable = false, columnDefinition = "TINYINT(1)")
  private Boolean isActive;
  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;
  @Column(name = "updated_at")
  private Instant updatedAt;
  @Column(nullable = false, unique = true)
  private String name;
  private String description;
}
