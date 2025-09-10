package sptech.school.projetoPI.core.application.usecases.role;

import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.gateways.RoleGateway;

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
