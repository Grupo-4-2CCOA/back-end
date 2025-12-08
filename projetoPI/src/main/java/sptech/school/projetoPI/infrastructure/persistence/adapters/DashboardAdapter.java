package sptech.school.projetoPI.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.DashboardSistemasMetrics;
import sptech.school.projetoPI.core.domains.DashboardVendasMetrics;
import sptech.school.projetoPI.core.domains.ServiceRanking;
import sptech.school.projetoPI.core.gateways.DashboardGateway;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaDashboardRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardAdapter implements DashboardGateway {

  private final JpaDashboardRepository repository;

  private static final String[] MESES_ABREV = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};

  @Override
  public DashboardSistemasMetrics getDashboardSistema(LocalDate startDate, LocalDate endDate) {
    List<Object[]> rendimentoRaw = repository.findRendimentoPorMes(startDate, endDate);
    List<Object[]> cancelamentoRaw = repository.findTaxaCancelamentoPorMes(startDate, endDate);
    int totalAtendimentos = repository.findTotalAtendimentos(startDate, endDate);
    List<ServiceRanking> ranking = repository.findRankingServicos(startDate, endDate);

    List<String> rendimentoLabels = new ArrayList<>();
    List<Double> rendimentoValues = new ArrayList<>();
    for (Object[] row : rendimentoRaw) {
      int ano = ((Number) row[0]).intValue();
      int mes = ((Number) row[1]).intValue();
      double valor = ((Number) row[2]).doubleValue();
      rendimentoLabels.add(MESES_ABREV[mes - 1] + " " + ano);
      rendimentoValues.add(valor);
    }

    List<String> cancelamentoLabels = new ArrayList<>();
    List<Double> cancelamentoValues = new ArrayList<>();
    for (Object[] row : cancelamentoRaw) {
      int ano = ((Number) row[0]).intValue();
      int mes = ((Number) row[1]).intValue();
      double taxa = ((Number) row[2]).doubleValue();
      cancelamentoLabels.add(MESES_ABREV[mes - 1] + " " + ano);
      cancelamentoValues.add(taxa);
    }

    return new DashboardSistemasMetrics(
      rendimentoLabels, rendimentoValues,
      cancelamentoLabels, cancelamentoValues,
      totalAtendimentos, ranking
    );
  }

  @Override
  public DashboardVendasMetrics getDashboardVendas(LocalDate startDate, LocalDate endDate) {
    List<Object[]> primeirosAgendamentosRaw = repository.findPrimeirosAgendamentosPorMes(startDate, endDate);
    List<Object[]> leadsRaw = repository.findLeadsPorMes(startDate, endDate);
    List<Object[]> taxaRetornoRaw = repository.findTaxaRetornoPorMes(startDate, endDate);
    List<Object[]> taxaConversaoRaw = repository.findTaxaConversaoPorMes(startDate, endDate);

    // Processa primeiros agendamentos: [ano, mes, valor] -> listas separadas
    List<String> primeirosAgendamentosLabels = new ArrayList<>();
    List<Double> primeirosAgendamentosValues = new ArrayList<>();
    for (Object[] row : primeirosAgendamentosRaw) {
      int ano = ((Number) row[0]).intValue();
      int mes = ((Number) row[1]).intValue();
      double valor = ((Number) row[2]).doubleValue();
      primeirosAgendamentosLabels.add(MESES_ABREV[mes - 1] + " " + ano);
      primeirosAgendamentosValues.add(valor);
    }

    // Processa leads: [ano, mes, valor] -> listas separadas
    List<String> leadsLabels = new ArrayList<>();
    List<Double> leadsValues = new ArrayList<>();
    for (Object[] row : leadsRaw) {
      int ano = ((Number) row[0]).intValue();
      int mes = ((Number) row[1]).intValue();
      double valor = ((Number) row[2]).doubleValue();
      leadsLabels.add(MESES_ABREV[mes - 1] + " " + ano);
      leadsValues.add(valor);
    }

    // Processa taxa de retorno: [ano, mes, valor] -> listas separadas
    List<String> taxaRetornoLabels = new ArrayList<>();
    List<Double> taxaRetornoValues = new ArrayList<>();
    for (Object[] row : taxaRetornoRaw) {
      int ano = ((Number) row[0]).intValue();
      int mes = ((Number) row[1]).intValue();
      double valor = ((Number) row[2]).doubleValue();
      taxaRetornoLabels.add(MESES_ABREV[mes - 1] + " " + ano);
      taxaRetornoValues.add(valor);
    }

    // Processa taxa de conversão: [ano, mes, taxa] -> listas separadas
    List<String> taxaConversaoLabels = new ArrayList<>();
    List<Double> taxaConversaoValues = new ArrayList<>();
    for (Object[] row : taxaConversaoRaw) {
      int ano = ((Number) row[0]).intValue();
      int mes = ((Number) row[1]).intValue();
      double taxa = ((Number) row[2]).doubleValue();
      taxaConversaoLabels.add(MESES_ABREV[mes - 1] + " " + ano);
      taxaConversaoValues.add(taxa);
    }

    return new DashboardVendasMetrics(
      primeirosAgendamentosLabels, primeirosAgendamentosValues,
      leadsLabels, leadsValues,
      taxaRetornoLabels, taxaRetornoValues,
      taxaConversaoLabels, taxaConversaoValues
    );
  }
}
