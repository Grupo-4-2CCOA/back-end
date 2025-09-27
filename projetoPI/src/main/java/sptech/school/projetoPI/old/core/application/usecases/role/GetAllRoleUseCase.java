package sptech.school.projetoPI.old.core.application.usecases.role;

import sptech.school.projetoPI.old.core.domains.RoleDomain;
import sptech.school.projetoPI.old.core.gateways.RoleGateway;

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
