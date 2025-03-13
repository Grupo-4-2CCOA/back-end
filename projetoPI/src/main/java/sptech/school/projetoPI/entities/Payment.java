package sptech.school.projetoPI.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String formaPagamento;
    private Date dataPagamento;
    private String numeroTransacao;
    private User usuarioPagamento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public User getUsuarioPagamento() {
        return usuarioPagamento;
    }

    public void setUsuarioPagamento(User usuarioPagamento) {
        this.usuarioPagamento = usuarioPagamento;
    }
}
