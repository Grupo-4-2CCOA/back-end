package sptech.school.projetoPI.core.application.dto.user;

import lombok.*;
import sptech.school.projetoPI.core.application.dto.role.RoleResumeResponseDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String cep;
    private RoleResumeResponseDto role;
}
