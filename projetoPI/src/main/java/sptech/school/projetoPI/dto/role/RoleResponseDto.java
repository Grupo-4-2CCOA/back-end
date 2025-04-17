package sptech.school.projetoPI.dto.role;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String description;
}
