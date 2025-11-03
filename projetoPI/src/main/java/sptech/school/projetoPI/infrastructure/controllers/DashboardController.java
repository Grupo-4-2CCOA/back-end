package sptech.school.projetoPI.infrastructure.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.projetoPI.core.application.dto.dashboard.DashboardResponseDto;
import sptech.school.projetoPI.core.application.dto.dashboard.ServiceRankDto;
import sptech.school.projetoPI.core.application.usecases.dashboard.GetDashboardSistemaUseCase;
import sptech.school.projetoPI.core.domains.DashboardMetrics;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final GetDashboardSistemaUseCase getDashboardSistemaUseCase;

    @GetMapping("/sistema")
    public ResponseEntity<DashboardResponseDto> getDashboardSistema(
            @RequestParam int mes,
            @RequestParam int ano) {

        DashboardMetrics metrics = getDashboardSistemaUseCase.execute(mes, ano);
        DashboardResponseDto response = new DashboardResponseDto(
                metrics.getRendimentoTotal(),
                metrics.getTaxaCancelamento(),
                metrics.getTotalAtendimentos(),
                metrics.getRankingServicos().stream()
                        .map(r -> new ServiceRankDto(r.getRanking(), r.getNomeServico(), r.getQuantidade(), r.getQuantidade()))
                        .toList()
        );

        return ResponseEntity.ok(response);
    }
}
