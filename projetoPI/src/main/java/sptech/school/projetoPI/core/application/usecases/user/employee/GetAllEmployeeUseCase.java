package sptech.school.projetoPI.core.application.usecases.user.employee;

import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.util.List;

public class GetAllEmployeeUseCase {

    private final UserGateway userGateway;

    public GetAllEmployeeUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public List<UserDomain> execute() {
        return userGateway.findAllByActiveTrue();
    }
}
