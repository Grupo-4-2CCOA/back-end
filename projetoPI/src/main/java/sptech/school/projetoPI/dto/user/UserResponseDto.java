package sptech.school.projetoPI.dto.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import sptech.school.projetoPI.enums.Role;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String cep;
}
