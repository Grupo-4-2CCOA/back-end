package sptech.school.projetoPI.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.core.domains.User;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmployeeJpaEntity extends User {
    @ManyToOne
    @JoinColumn(name = "fk_role")
    private Role role;
}
