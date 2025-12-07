package sptech.school.projetoPI.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.DashboardSistemasMetrics;
import sptech.school.projetoPI.core.domains.DashboardVendasMetrics;
import sptech.school.projetoPI.core.domains.ServiceRanking;
import sptech.school.projetoPI.core.gateways.DashboardGateway;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaDashboardRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardAdapter implements DashboardGateway {

  private final JpaDashboardRepository repository;

  @Override
  public DashboardSistemasMetrics getDashboardSistema(LocalDate startDate, LocalDate endDate) {
    List<Object[]> rendimento = repository.findRendimentoPorSemana(startDate, endDate);
    List<Object[]> taxaCancelamento = repository.findTaxaCancelamentoPorSemana(startDate, endDate);
    int totalAtendimentos = repository.findTotalAtendimentos(startDate, endDate);
    List<ServiceRanking> ranking = repository.findRankingServicos(startDate, endDate);

    return new DashboardSistemasMetrics(rendimento, taxaCancelamento, totalAtendimentos, ranking);
  }

  @Override
  public DashboardVendasMetrics getDashboardVendas(LocalDate startDate, LocalDate endDate) {
    List<Object[]> firstSchedules = repository.findPrimeirosAgendamentosPorSemana(startDate, endDate);
    List<Object[]> visitors = repository.findLeadsPorSemana(startDate, endDate);
    List<Object[]> returnRate = repository.findTaxaRetornoPorSemana(startDate, endDate);

    return new DashboardVendasMetrics(firstSchedules, visitors, returnRate);
  }
}
