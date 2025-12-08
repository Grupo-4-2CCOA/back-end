package sptech.school.projetoPI.core.application.usecases.dashboard;

import sptech.school.projetoPI.core.domains.DashboardSistemasMetrics;
import sptech.school.projetoPI.core.gateways.DashboardGateway;

import java.time.LocalDate;

public class GetDashboardSistemaUseCase {
    private final DashboardGateway dashboardGateway;

    public GetDashboardSistemaUseCase(DashboardGateway dashboardGateway) {
        this.dashboardGateway = dashboardGateway;
    }

    public DashboardSistemasMetrics execute(LocalDate startDate, LocalDate endDate) {
        return dashboardGateway.getDashboardSistema(startDate, endDate);
    }
}
