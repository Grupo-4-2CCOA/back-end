package sptech.school.projetoPI.core.application.usecases.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErroResponse {
    private Integer status;
    private String erro;
    private String caminho;
    private LocalDateTime timestamp;
}
