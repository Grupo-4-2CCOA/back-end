package sptech.school.projetoPI.core.application.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardVendasResponseDto {
    private MetricaMensalDto primeirosAgendamentos;
    private MetricaMensalDto leads;
    private MetricaMensalDto taxaRetorno;
    private MetricaMensalDto taxaConversao;
}
