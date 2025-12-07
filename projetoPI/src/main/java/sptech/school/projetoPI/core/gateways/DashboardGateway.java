package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.DashboardSistemasMetrics;
import sptech.school.projetoPI.core.domains.DashboardVendasMetrics;

import java.time.LocalDate;

public interface DashboardGateway {
  DashboardSistemasMetrics getDashboardSistema(LocalDate startDate, LocalDate endDate);
  DashboardVendasMetrics getDashboardVendas(LocalDate startDate, LocalDate endDate);
}
