package sptech.school.projetoPI.Pagamento;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import sptech.school.projetoPI.Usuario.Usuario;

import java.util.Date;

@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPreco;
    private String formaPagamento;
    private Date dataPagamento;
    private String numeroTransacao;
    private Usuario usuarioPagamento;

    public int getIdPreco() {
        return idPreco;
    }

    public void setIdPreco(int idPreco) {
        this.idPreco = idPreco;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getNumeroTransacao() {
        return numeroTransacao;
    }

    public void setNumeroTransacao(String numeroTransacao) {
        this.numeroTransacao = numeroTransacao;
    }

    public Usuario getUsuarioPagamento() {
        return usuarioPagamento;
    }

    public void setUsuarioPagamento(Usuario usuarioPagamento) {
        this.usuarioPagamento = usuarioPagamento;
    }
}
