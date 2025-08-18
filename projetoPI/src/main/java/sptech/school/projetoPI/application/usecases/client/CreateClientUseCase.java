package sptech.school.projetoPI.application.usecases.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.application.usecases.user.UserValidationUseCase; // Assumindo que você criou um Use Case de validação
import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.enums.Logs;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreateClientUseCase {
    private final ClientGateway clientGateway;
    private final PasswordEncoder passwordEncoder;
    private final UserValidationUseCase userValidationUseCase;

    public Client execute(Client client) {
        userValidationUseCase.validateUniqueProperties(client.getCpf(), client.getEmail(), client.getPhone());

        String encodedPassword = passwordEncoder.encode(client.getPassword());

        client.setId(null);
        client.setPassword(encodedPassword);
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());

        log.info(Logs.POST_SUCCESSFULLY.getMessage());
        return clientGateway.save(client);
    }
}