package sptech.school.projetoPI.core.application.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRankDto {
    private int ranking;
    private String nomeServico;
    private long quantidade;
    private double valorTotal;
}
