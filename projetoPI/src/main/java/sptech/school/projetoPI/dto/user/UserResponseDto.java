package sptech.school.projetoPI.dto.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import sptech.school.projetoPI.dto.role.RoleResumeResponseDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String cep;
    private RoleResumeResponseDto role;
}
