package sptech.school.projetoPI.core.application.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResumeResponseDto {
    private Integer id;
    private String name;
    private String email;
    private Integer role;
}
