package sptech.school.projetoPI.core.application.command.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResumeResponseDto {
    private Integer id;
    private String name;
    private String email;
}
