package sptech.school.projetoPI.core.domains;

import java.util.List;

public class DashboardMetrics {
    private List<Object[]> rendimentoTotal;
    private List<Object[]> taxaCancelamento;
    private int totalAtendimentos;
    private List<ServiceRanking> rankingServicos;

    public DashboardMetrics(List<Object[]> rendimentoTotal, List<Object[]> taxaCancelamento, int totalAtendimentos, List<ServiceRanking> rankingServicos) {
        this.rendimentoTotal = rendimentoTotal;
        this.taxaCancelamento = taxaCancelamento;
        this.totalAtendimentos = totalAtendimentos;
        this.rankingServicos = rankingServicos;
    }

    public DashboardMetrics() {
    }

    public List<Object[]> getRendimentoTotal() {
        return rendimentoTotal;
    }

    public void setRendimentoTotal(List<Object[]> rendimentoTotal) {
        this.rendimentoTotal = rendimentoTotal;
    }

    public List<Object[]> getTaxaCancelamento() {
        return taxaCancelamento;
    }

    public void setTaxaCancelamento(List<Object[]> taxaCancelamento) {
        this.taxaCancelamento = taxaCancelamento;
    }

    public int getTotalAtendimentos() {
        return totalAtendimentos;
    }

    public void setTotalAtendimentos(int totalAtendimentos) {
        this.totalAtendimentos = totalAtendimentos;
    }

    public List<ServiceRanking> getRankingServicos() {
        return rankingServicos;
    }

    public void setRankingServicos(List<ServiceRanking> rankingServicos) {
        this.rankingServicos = rankingServicos;
    }
}
