package sptech.school.projetoPI.application.usecases.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.core.gateways.RoleGateway;

import java.util.List;

public class GetAllRoleUseCase {
    private final RoleGateway roleGateway;

    public GetAllRoleUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public List<Role> execute() {
        return roleGateway.findAllByActiveTrue();
    }
}
