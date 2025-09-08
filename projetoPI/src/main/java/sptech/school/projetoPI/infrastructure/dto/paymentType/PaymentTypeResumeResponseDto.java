package sptech.school.projetoPI.infrastructure.dto.paymentType;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentTypeResumeResponseDto {
    private Integer id;
    private String name;
}
