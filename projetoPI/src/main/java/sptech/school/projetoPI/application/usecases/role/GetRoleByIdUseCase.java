package sptech.school.projetoPI.application.usecases.role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Role;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

public class GetRoleByIdUseCase {
    private final RoleGateway roleGateway;

    public GetRoleByIdUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public Role execute(Integer id) {
        return roleGateway.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O cargo com o ID %d não foi encontrada".formatted(id)
                )
        );
    }
}
