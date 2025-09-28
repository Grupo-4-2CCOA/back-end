package sptech.school.projetoPI.old.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employee")
public class EmployeeJpaEntity extends UserJpaEntity {
    @ManyToOne
    @JoinColumn(name = "fk_role")
    private RoleJpaEntity role;
}
