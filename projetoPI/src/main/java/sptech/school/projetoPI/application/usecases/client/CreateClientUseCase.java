package sptech.school.projetoPI.application.usecases.client;

import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.core.domains.Client;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import java.time.LocalDateTime;

public class CreateClientUseCase {
    private final ClientGateway clientGateway;
    private final PasswordEncoder passwordEncoder;

    public CreateClientUseCase(ClientGateway clientGateway, PasswordEncoder passwordEncoder) {
        this.clientGateway = clientGateway;
        this.passwordEncoder = passwordEncoder;
    }

    public Client execute(Client client) {
        if (clientGateway.existsByCpf(client.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }

        if (clientGateway.existsByEmailIgnoreCase(client.getEmail())) {
            throw new ConflictException("E-mail já cadastrado");
        }

        if (clientGateway.existsByPhone(client.getPhone())) {
            throw new ConflictException("Telefone já cadastrado");
        }

        String encodedPassword = passwordEncoder.encode(client.getPassword());

        client.setId(null);
        client.setPassword(encodedPassword);
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        return clientGateway.save(client);
    }
}