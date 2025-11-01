package sptech.school.projetoPI.core.application.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDto {
    private double rendimentoTotal;
    private double taxaCancelamento;
    private int totalAtendimentos;
    private List<ServiceRankDto> rankingServicos;
}