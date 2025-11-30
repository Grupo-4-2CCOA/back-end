package sptech.school.projetoPI.core.domains;

import java.util.List;

public class DashboardVendasMetrics {
  private List<Object[]> primeirosAgendamentos;
  private List<Object[]> leads;
  private List<Object[]> taxaRetorno;

  public DashboardVendasMetrics(List<Object[]> primeirosAgendamentos, List<Object[]> leads, List<Object[]> taxaRetorno) {
    this.primeirosAgendamentos = primeirosAgendamentos;
    this.leads = leads;
    this.taxaRetorno = taxaRetorno;
  }

  public DashboardVendasMetrics() {
  }

  public List<Object[]> getPrimeirosAgendamentos() {
    return primeirosAgendamentos;
  }

  public void setPrimeirosAgendamentos(List<Object[]> primeirosAgendamentos) {
    this.primeirosAgendamentos = primeirosAgendamentos;
  }

  public List<Object[]> getLeads() {
    return leads;
  }

  public void setLeads(List<Object[]> leads) {
    this.leads = leads;
  }

  public List<Object[]> getTaxaRetorno() {
    return taxaRetorno;
  }

  public void setTaxaRetorno(List<Object[]> taxaRetorno) {
    this.taxaRetorno = taxaRetorno;
  }
}
