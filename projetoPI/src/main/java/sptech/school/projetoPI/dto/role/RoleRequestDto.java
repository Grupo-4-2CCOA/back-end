package sptech.school.projetoPI.dto.role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequestDto {
    @Size(max = 80, message = "Nome muito longo")
    @NotBlank(message = "Insira o nome do cargo")
    @Pattern(regexp = "(?i)OWNER|CUSTOMER|EMPLOYEE", message = "O nome do cargo deve ser OWNER, CUSTOMER ou EMPLOYEE")
    private String name;

    @Size(max = 255, message = "Descrição muito longa")
    private String description;
}
