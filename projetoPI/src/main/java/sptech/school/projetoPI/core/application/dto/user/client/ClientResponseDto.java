package sptech.school.projetoPI.core.application.dto.user.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import sptech.school.projetoPI.core.application.dto.role.RoleResumeResponseDto;
import sptech.school.projetoPI.core.domains.RoleDomain;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponseDto {
    private Integer id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String cep;
    private RoleResumeResponseDto role;
}
