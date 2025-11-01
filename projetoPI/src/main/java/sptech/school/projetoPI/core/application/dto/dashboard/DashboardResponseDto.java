package sptech.school.projetoPI.core.application.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDto {
    private List<Object[]> rendimentoTotal;
    private List<Object[]> taxaCancelamento;
    private int totalAtendimentos;
    private List<ServiceRankDto> rankingServicos;
}