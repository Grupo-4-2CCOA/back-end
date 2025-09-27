package sptech.school.projetoPI.old.core.application.usecases.role;

import sptech.school.projetoPI.old.core.domains.RoleDomain;
import sptech.school.projetoPI.old.core.gateways.RoleGateway;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;

public class GetRoleByIdUseCase {
    private final RoleGateway roleGateway;

    public GetRoleByIdUseCase(RoleGateway roleGateway) {
        this.roleGateway = roleGateway;
    }

    public RoleDomain execute(Integer id) {
        return roleGateway.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O cargo com o ID %d não foi encontrada".formatted(id)
                )
        );
    }
}
