package sptech.school.projetoPI.infrastructure.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sptech.school.projetoPI.core.application.usecases.dashboard.GetDashboardSistemaUseCase;
import sptech.school.projetoPI.core.application.usecases.dashboard.GetDashboardVendasUseCase;
import sptech.school.projetoPI.core.gateways.DashboardGateway;

@Configuration
public class DashboardConfig {

  @Bean
  public GetDashboardSistemaUseCase getDashboardSistemaUseCase(DashboardGateway dashboardGateway) {
    return new GetDashboardSistemaUseCase(dashboardGateway);
  }

  @Bean
  public GetDashboardVendasUseCase getDashboardVendasUseCase(DashboardGateway dashboardGateway) {
    return new GetDashboardVendasUseCase(dashboardGateway);
  }
}
