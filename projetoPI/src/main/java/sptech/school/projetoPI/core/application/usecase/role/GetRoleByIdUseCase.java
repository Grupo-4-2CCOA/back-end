package sptech.school.projetoPI.core.application.usecase.role;

import sptech.school.projetoPI.core.domain.RoleDomain;
import sptech.school.projetoPI.core.gateway.RoleGateway;
import sptech.school.projetoPI.core.application.usecase.exceptions.exceptionClass.EntityNotFoundException;

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
