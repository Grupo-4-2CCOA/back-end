package sptech.school.projetoPI.infrastructure.dto.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResumeResponseDto {
    private Integer id;
    private String name;
    private String email;
}
