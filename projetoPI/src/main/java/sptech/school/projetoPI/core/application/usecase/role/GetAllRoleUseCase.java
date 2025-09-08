package sptech.school.projetoPI.core.application.usecase.role;

import sptech.school.projetoPI.core.domain.RoleDomain;
import sptech.school.projetoPI.core.gateway.RoleGateway;

import java.util.List;

public class GetAllRoleUseCase {
    private final RoleGateway roleGateway;

    public GetAllRoleUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public List<RoleDomain> execute() {
        return roleGateway.findAllByActiveTrue();
    }
}
