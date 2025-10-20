package sptech.school.projetoPI.core.application.dto.user.employee;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.core.application.dto.role.RoleResumeResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResponseDto {
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String cep;
    private RoleResumeResponseDto role;
}
