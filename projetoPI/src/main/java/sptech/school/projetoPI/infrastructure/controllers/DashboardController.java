package sptech.school.projetoPI.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.projetoPI.core.application.dto.dashboard.DashboardSistemaResponseDto;
import sptech.school.projetoPI.core.application.dto.dashboard.DashboardVendasResponseDto;
import sptech.school.projetoPI.core.application.dto.dashboard.MetricaMensalDto;
import sptech.school.projetoPI.core.application.dto.dashboard.ServiceRankDto;
import sptech.school.projetoPI.core.application.usecases.dashboard.GetDashboardSistemaUseCase;
import sptech.school.projetoPI.core.application.usecases.dashboard.GetDashboardVendasUseCase;
import sptech.school.projetoPI.core.domains.DashboardSistemasMetrics;
import sptech.school.projetoPI.core.domains.DashboardVendasMetrics;

import java.time.LocalDate;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final GetDashboardSistemaUseCase getDashboardSistemaUseCase;
    private final GetDashboardVendasUseCase getDashboardVendasUseCase;

    @GetMapping("/sistema")
    public ResponseEntity<DashboardSistemaResponseDto> getDashboardSistema(
            @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {

        DashboardSistemasMetrics metrics = getDashboardSistemaUseCase.execute(startDate, endDate);
        
        MetricaMensalDto rendimentoTotal = new MetricaMensalDto(
            metrics.getRendimentoLabels(),
            metrics.getRendimentoValues()
        );
        
        MetricaMensalDto taxaCancelamento = new MetricaMensalDto(
            metrics.getCancelamentoLabels(),
            metrics.getCancelamentoValues()
        );
        
        DashboardSistemaResponseDto response = new DashboardSistemaResponseDto(
            rendimentoTotal,
            taxaCancelamento,
            metrics.getTotalAtendimentos(),
            metrics.getRankingServicos().stream()
                .map(r -> new ServiceRankDto(r.getRanking(), r.getNomeServico(), r.getQuantidade(), r.getValorTotal()))
                .toList()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendas")
    public ResponseEntity<DashboardVendasResponseDto> getDashboardVendas(
        @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {

        DashboardVendasMetrics metrics = getDashboardVendasUseCase.execute(startDate, endDate);
        
        MetricaMensalDto primeirosAgendamentos = new MetricaMensalDto(
            metrics.getPrimeirosAgendamentosLabels(),
            metrics.getPrimeirosAgendamentosValues()
        );
        
        MetricaMensalDto leads = new MetricaMensalDto(
            metrics.getLeadsLabels(),
            metrics.getLeadsValues()
        );
        
        MetricaMensalDto taxaRetorno = new MetricaMensalDto(
            metrics.getTaxaRetornoLabels(),
            metrics.getTaxaRetornoValues()
        );
        
        MetricaMensalDto taxaConversao = new MetricaMensalDto(
            metrics.getTaxaConversaoLabels(),
            metrics.getTaxaConversaoValues()
        );
        
        DashboardVendasResponseDto response = new DashboardVendasResponseDto(
            primeirosAgendamentos,
            leads,
            taxaRetorno,
            taxaConversao
        );

        return ResponseEntity.ok(response);
    }
}
