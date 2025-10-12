package sptech.school.projetoPI.core.application.usecases.user.client;

import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.gateways.UserGateway;

public class GetClientByIdUseCase {

    private final UserGateway userGateway;

    public GetClientByIdUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserDomain execute(Integer id) {
        return userGateway.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Id para cliente não encontrado"));
    }
}
