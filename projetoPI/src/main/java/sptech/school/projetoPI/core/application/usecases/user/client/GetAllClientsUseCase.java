package sptech.school.projetoPI.core.application.usecases.user.client;

import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.util.List;

public class GetAllClientsUseCase {
    private final UserGateway userGateway;

    public GetAllClientsUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<UserDomain> execute() {
        List<UserDomain> userDomains = userGateway.findAllByActiveTrue();
        return userDomains;
    }
}