package sptech.school.projetoPI.core.application.usecases.dashboard;

import sptech.school.projetoPI.core.domains.DashboardVendasMetrics;
import sptech.school.projetoPI.core.gateways.DashboardGateway;

public class GetDashboardVendasUseCase {
    private final DashboardGateway dashboardGateway;

    public GetDashboardVendasUseCase(DashboardGateway dashboardGateway) {
        this.dashboardGateway = dashboardGateway;
    }

    public DashboardVendasMetrics execute(int mes, int ano) {
        return dashboardGateway.getDashboardVendas(mes, ano);
    }
}
