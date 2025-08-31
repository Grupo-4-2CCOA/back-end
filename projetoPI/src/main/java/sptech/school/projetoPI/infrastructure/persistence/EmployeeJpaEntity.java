package sptech.school.projetoPI.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sptech.school.projetoPI.core.domains.User;

import javax.management.relation.Role;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmployeeJpaEntity extends UserJpaEntity {
    @ManyToOne
    @JoinColumn(name = "fk_role")
    private RoleJpaEntity role;
}
