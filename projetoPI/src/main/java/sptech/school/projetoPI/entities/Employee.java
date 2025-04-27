package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee extends User {
    @ManyToOne
    @JoinColumn(name = "fk_role")
    private Role role;
}
