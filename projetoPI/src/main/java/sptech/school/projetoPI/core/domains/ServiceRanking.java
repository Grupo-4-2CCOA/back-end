package sptech.school.projetoPI.core.domains;

public class ServiceRanking {
    private String nomeServico;
    private long quantidade;

    public ServiceRanking(String nomeServico, long quantidade) {
        this.nomeServico = nomeServico;
        this.quantidade = quantidade;
    }

    public ServiceRanking() {
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
}
