package sptech.school.projetoPI.core.domains;

public class ServiceRanking {
    private int ranking;
    private String nomeServico;
    private long quantidade;
    private double valorTotal;

    public ServiceRanking(int ranking, String nomeServico, long quantidade, double valorTotal) {
        this.ranking = ranking;
        this.nomeServico = nomeServico;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    public ServiceRanking() {
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
