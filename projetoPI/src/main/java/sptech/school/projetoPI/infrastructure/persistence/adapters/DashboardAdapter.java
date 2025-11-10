package sptech.school.projetoPI.infrastructure.persistence.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.DashboardMetrics;
import sptech.school.projetoPI.core.domains.ServiceRanking;
import sptech.school.projetoPI.core.gateways.DashboardGateway;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaDashboardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardAdapter implements DashboardGateway {

    private final JpaDashboardRepository repository;

    @Override
    public DashboardMetrics getDashboardSistema(int mes, int ano) {
        List<Object[]> rendimento = repository.findRendimentoPorSemana(mes, ano);
        List<Object[]> taxaCancelamento = repository.findTaxaCancelamentoPorSemana(mes, ano);
        int totalAtendimentos = repository.findTotalAtendimentos(mes, ano);
        List<ServiceRanking> ranking = repository.findRankingServicos(mes, ano);

        return new DashboardMetrics(rendimento, taxaCancelamento, totalAtendimentos, ranking);
    }
}
