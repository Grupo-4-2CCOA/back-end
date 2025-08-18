package sptech.school.projetoPI.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;
import sptech.school.projetoPI.core.domains.Category;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ServiceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private Double basePrice;
    private Integer baseDuration;
    private String description;
    private String image;

    @ManyToOne
    @JoinColumn(name="fk_category")
    private Category category;
}
