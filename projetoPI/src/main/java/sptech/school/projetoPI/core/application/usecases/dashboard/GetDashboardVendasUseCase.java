package sptech.school.projetoPI.core.application.usecases.dashboard;

import sptech.school.projetoPI.core.domains.DashboardVendasMetrics;
import sptech.school.projetoPI.core.gateways.DashboardGateway;

import java.time.LocalDate;

public class GetDashboardVendasUseCase {
    private final DashboardGateway dashboardGateway;

    public GetDashboardVendasUseCase(DashboardGateway dashboardGateway) {
        this.dashboardGateway = dashboardGateway;
    }

    public DashboardVendasMetrics execute(LocalDate startDate, LocalDate endDate) {
        return dashboardGateway.getDashboardVendas(startDate, endDate);
    }
}
