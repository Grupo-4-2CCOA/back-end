package sptech.school.projetoPI.application.usecases.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.enums.Logs;
import java.util.List;

public class GetAllClientsUseCase {
    private final ClientGateway clientGateway;

    public GetAllClientsUseCase(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }

    public List<Client> execute() {
        List<Client> clients = clientGateway.findAllByActiveTrue();
        return clients;
    }
}