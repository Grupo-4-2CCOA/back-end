package sptech.school.projetoPI.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Service {
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
