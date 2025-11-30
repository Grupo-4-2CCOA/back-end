package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.DashboardSistemasMetrics;
import sptech.school.projetoPI.core.domains.DashboardVendasMetrics;

public interface DashboardGateway {
  DashboardSistemasMetrics getDashboardSistema(int mes, int ano);
  DashboardVendasMetrics getDashboardVendas(int mes, int ano);
}
