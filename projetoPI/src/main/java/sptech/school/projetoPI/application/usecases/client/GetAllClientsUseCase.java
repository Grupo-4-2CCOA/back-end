package sptech.school.projetoPI.application.usecases.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.enums.Logs;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAllClientsUseCase {
    private final ClientGateway clientGateway;

    public List<Client> execute() {
        List<Client> clients = clientGateway.findAllByActiveTrue();
        if (clients.isEmpty()) {
            log.info(Logs.GET_ALL_SUCCESSFULLY_EMPTY.getMessage());
        } else {
            log.info(Logs.GET_ALL_SUCCESSFULLY.getMessage());
        }
        return clients;
    }
}