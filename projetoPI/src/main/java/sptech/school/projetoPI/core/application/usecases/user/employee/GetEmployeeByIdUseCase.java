package sptech.school.projetoPI.core.application.usecases.user.employee;

import sptech.school.projetoPI.core.application.usecases.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.core.gateways.UserGateway;

public class GetEmployeeByIdUseCase {

    private final UserGateway userGateway;

    public GetEmployeeByIdUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public UserDomain execute(Integer id) {
        return userGateway.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                     new EntityNotFoundException(
                            Logs.GET_BY_ID_NOT_FOUND.getMessage().formatted(id)
                    ));
    }
}
