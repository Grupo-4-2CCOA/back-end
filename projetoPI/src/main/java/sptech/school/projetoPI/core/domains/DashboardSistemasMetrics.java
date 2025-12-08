package sptech.school.projetoPI.core.domains;

import java.util.List;

public class DashboardSistemasMetrics {
    private List<String> rendimentoLabels;
    private List<Double> rendimentoValues;
    private List<String> cancelamentoLabels;
    private List<Double> cancelamentoValues;
    private int totalAtendimentos;
    private List<ServiceRanking> rankingServicos;

    public DashboardSistemasMetrics(List<String> rendimentoLabels, List<Double> rendimentoValues,
                                    List<String> cancelamentoLabels, List<Double> cancelamentoValues,
                                    int totalAtendimentos, List<ServiceRanking> rankingServicos) {
        this.rendimentoLabels = rendimentoLabels;
        this.rendimentoValues = rendimentoValues;
        this.cancelamentoLabels = cancelamentoLabels;
        this.cancelamentoValues = cancelamentoValues;
        this.totalAtendimentos = totalAtendimentos;
        this.rankingServicos = rankingServicos;
    }

    public DashboardSistemasMetrics() {
    }

    public List<String> getRendimentoLabels() {
        return rendimentoLabels;
    }

    public void setRendimentoLabels(List<String> rendimentoLabels) {
        this.rendimentoLabels = rendimentoLabels;
    }

    public List<Double> getRendimentoValues() {
        return rendimentoValues;
    }

    public void setRendimentoValues(List<Double> rendimentoValues) {
        this.rendimentoValues = rendimentoValues;
    }

    public List<String> getCancelamentoLabels() {
        return cancelamentoLabels;
    }

    public void setCancelamentoLabels(List<String> cancelamentoLabels) {
        this.cancelamentoLabels = cancelamentoLabels;
    }

    public List<Double> getCancelamentoValues() {
        return cancelamentoValues;
    }

    public void setCancelamentoValues(List<Double> cancelamentoValues) {
        this.cancelamentoValues = cancelamentoValues;
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
