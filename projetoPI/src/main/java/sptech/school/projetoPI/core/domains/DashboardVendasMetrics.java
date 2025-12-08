package sptech.school.projetoPI.core.domains;

import java.util.List;

public class DashboardVendasMetrics {
  private List<String> primeirosAgendamentosLabels;
  private List<Double> primeirosAgendamentosValues;
  private List<String> leadsLabels;
  private List<Double> leadsValues;
  private List<String> taxaRetornoLabels;
  private List<Double> taxaRetornoValues;
  private List<String> taxaConversaoLabels;
  private List<Double> taxaConversaoValues;

  public DashboardVendasMetrics(List<String> primeirosAgendamentosLabels, List<Double> primeirosAgendamentosValues,
                                 List<String> leadsLabels, List<Double> leadsValues,
                                 List<String> taxaRetornoLabels, List<Double> taxaRetornoValues,
                                 List<String> taxaConversaoLabels, List<Double> taxaConversaoValues) {
    this.primeirosAgendamentosLabels = primeirosAgendamentosLabels;
    this.primeirosAgendamentosValues = primeirosAgendamentosValues;
    this.leadsLabels = leadsLabels;
    this.leadsValues = leadsValues;
    this.taxaRetornoLabels = taxaRetornoLabels;
    this.taxaRetornoValues = taxaRetornoValues;
    this.taxaConversaoLabels = taxaConversaoLabels;
    this.taxaConversaoValues = taxaConversaoValues;
  }

  public DashboardVendasMetrics() {
  }

  public List<String> getPrimeirosAgendamentosLabels() {
    return primeirosAgendamentosLabels;
  }

  public void setPrimeirosAgendamentosLabels(List<String> primeirosAgendamentosLabels) {
    this.primeirosAgendamentosLabels = primeirosAgendamentosLabels;
  }

  public List<Double> getPrimeirosAgendamentosValues() {
    return primeirosAgendamentosValues;
  }

  public void setPrimeirosAgendamentosValues(List<Double> primeirosAgendamentosValues) {
    this.primeirosAgendamentosValues = primeirosAgendamentosValues;
  }

  public List<String> getLeadsLabels() {
    return leadsLabels;
  }

  public void setLeadsLabels(List<String> leadsLabels) {
    this.leadsLabels = leadsLabels;
  }

  public List<Double> getLeadsValues() {
    return leadsValues;
  }

  public void setLeadsValues(List<Double> leadsValues) {
    this.leadsValues = leadsValues;
  }

  public List<String> getTaxaRetornoLabels() {
    return taxaRetornoLabels;
  }

  public void setTaxaRetornoLabels(List<String> taxaRetornoLabels) {
    this.taxaRetornoLabels = taxaRetornoLabels;
  }

  public List<Double> getTaxaRetornoValues() {
    return taxaRetornoValues;
  }

  public void setTaxaRetornoValues(List<Double> taxaRetornoValues) {
    this.taxaRetornoValues = taxaRetornoValues;
  }

  public List<String> getTaxaConversaoLabels() {
    return taxaConversaoLabels;
  }

  public void setTaxaConversaoLabels(List<String> taxaConversaoLabels) {
    this.taxaConversaoLabels = taxaConversaoLabels;
  }

  public List<Double> getTaxaConversaoValues() {
    return taxaConversaoValues;
  }

  public void setTaxaConversaoValues(List<Double> taxaConversaoValues) {
    this.taxaConversaoValues = taxaConversaoValues;
  }
}
