package sptech.school.projetoPI.core.application.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardVendasResponseDto {
    private List<Object[]> primeirosAgendamentos;
    private List<Object[]> leads;
    private List<Object[]> taxaRetorno;
}
