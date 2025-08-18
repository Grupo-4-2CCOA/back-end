package sptech.school.projetoPI.application.usecases.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAllRoleUseCase {
    private final RoleGateway roleGateway;

    public List<Role> execute() {
        return roleGateway.findAllByActiveTrue();
    }
}
