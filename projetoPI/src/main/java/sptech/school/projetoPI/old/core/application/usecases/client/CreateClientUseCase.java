package sptech.school.projetoPI.old.core.application.usecases.client;

import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.old.core.domains.ClientDomain;
import sptech.school.projetoPI.old.core.gateways.ClientGateway;
import java.time.LocalDateTime;

public class CreateClientUseCase {
    private final ClientGateway clientGateway;
    private final PasswordEncoder passwordEncoder;

    public CreateClientUseCase(ClientGateway clientGateway, PasswordEncoder passwordEncoder) {
        this.clientGateway = clientGateway;
        this.passwordEncoder = passwordEncoder;
    }

    public ClientDomain execute(ClientDomain clientDomain) {
        if (clientGateway.existsByCpf(clientDomain.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }

        if (clientGateway.existsByEmailIgnoreCase(clientDomain.getEmail())) {
            throw new ConflictException("E-mail já cadastrado");
        }

        if (clientGateway.existsByPhone(clientDomain.getPhone())) {
            throw new ConflictException("Telefone já cadastrado");
        }

        String encodedPassword = passwordEncoder.encode(clientDomain.getPassword());

        clientDomain.setId(null);
        clientDomain.setPassword(encodedPassword);
        clientDomain.setCreatedAt(LocalDateTime.now());
        clientDomain.setUpdatedAt(LocalDateTime.now());
        return clientGateway.save(clientDomain);
    }
}