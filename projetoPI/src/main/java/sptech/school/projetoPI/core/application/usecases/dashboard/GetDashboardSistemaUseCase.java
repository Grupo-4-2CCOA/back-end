package sptech.school.projetoPI.core.application.usecases.dashboard;

import sptech.school.projetoPI.core.domains.DashboardSistemasMetrics;
import sptech.school.projetoPI.core.gateways.DashboardGateway;

public class GetDashboardSistemaUseCase {
    private final DashboardGateway dashboardGateway;

    public GetDashboardSistemaUseCase(DashboardGateway dashboardGateway) {
        this.dashboardGateway = dashboardGateway;
    }

    public DashboardSistemasMetrics execute(int mes, int ano) {
        return dashboardGateway.getDashboardSistema(mes, ano);
    }
}
