package sptech.school.projetoPI.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.DashboardSistemasMetrics;
import sptech.school.projetoPI.core.domains.DashboardVendasMetrics;
import sptech.school.projetoPI.core.domains.ServiceRanking;
import sptech.school.projetoPI.core.gateways.DashboardGateway;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaDashboardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardAdapter implements DashboardGateway {

  private final JpaDashboardRepository repository;

  @Override
  public DashboardSistemasMetrics getDashboardSistema(int mes, int ano) {
    List<Object[]> rendimento = repository.findRendimentoPorSemana(mes, ano);
    List<Object[]> taxaCancelamento = repository.findTaxaCancelamentoPorSemana(mes, ano);
    int totalAtendimentos = repository.findTotalAtendimentos(mes, ano);
    List<ServiceRanking> ranking = repository.findRankingServicos(mes, ano);

    return new DashboardSistemasMetrics(rendimento, taxaCancelamento, totalAtendimentos, ranking);
  }

  @Override
  public DashboardVendasMetrics getDashboardVendas(int mes, int ano) {
    List<Object[]> firstSchedules = repository.findPrimeirosAgendamentosPorSemana(mes, ano);
    List<Object[]> visitors = repository.findLeadsPorSemana(mes, ano);
    List<Object[]> returnRate = repository.findTaxaRetornoPorSemana(mes, ano);

    return new DashboardVendasMetrics(firstSchedules, visitors, returnRate);
  }
}
