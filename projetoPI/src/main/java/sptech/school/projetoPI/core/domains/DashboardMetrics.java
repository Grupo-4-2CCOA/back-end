package sptech.school.projetoPI.core.domains;

import java.util.List;

public class DashboardMetrics {
    private double rendimentoTotal;
    private double taxaCancelamento;
    private int totalAtendimentos;
    private List<ServiceRanking> rankingServicos;

    public DashboardMetrics(double rendimentoTotal, double taxaCancelamento, int totalAtendimentos, List<ServiceRanking> rankingServicos) {
        this.rendimentoTotal = rendimentoTotal;
        this.taxaCancelamento = taxaCancelamento;
        this.totalAtendimentos = totalAtendimentos;
        this.rankingServicos = rankingServicos;
    }

    public DashboardMetrics() {
    }

    public double getRendimentoTotal() {
        return rendimentoTotal;
    }

    public void setRendimentoTotal(double rendimentoTotal) {
        this.rendimentoTotal = rendimentoTotal;
    }

    public double getTaxaCancelamento() {
        return taxaCancelamento;
    }

    public void setTaxaCancelamento(double taxaCancelamento) {
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
