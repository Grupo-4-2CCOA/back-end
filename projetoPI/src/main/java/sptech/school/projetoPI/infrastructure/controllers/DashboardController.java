package sptech.school.projetoPI.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.projetoPI.core.application.dto.dashboard.DashboardSistemaResponseDto;
import sptech.school.projetoPI.core.application.dto.dashboard.DashboardVendasResponseDto;
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
        DashboardSistemaResponseDto response = new DashboardSistemaResponseDto(
                metrics.getRendimentoTotal(),
                metrics.getTaxaCancelamento(),
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
    DashboardVendasResponseDto response = new DashboardVendasResponseDto(
      metrics.getPrimeirosAgendamentos(),
      metrics.getLeads(),
      metrics.getTaxaRetorno()
    );

    return ResponseEntity.ok(response);
  }
}
