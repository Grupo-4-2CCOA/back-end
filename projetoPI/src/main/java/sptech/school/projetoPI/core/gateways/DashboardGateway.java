package sptech.school.projetoPI.core.gateways;

import sptech.school.projetoPI.core.domains.DashboardMetrics;

public interface DashboardGateway {
    DashboardMetrics getDashboardSistema(int mes, int ano);
}
