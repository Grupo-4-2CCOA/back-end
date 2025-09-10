package sptech.school.projetoPI.core.application.dto.role;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResumeResponseDto {
    private Integer id;
    private String name;
}
