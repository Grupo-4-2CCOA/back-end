package sptech.school.projetoPI.core.application.usecases.user.client;

import sptech.school.projetoPI.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;

import java.time.LocalDateTime;

public class CreateClientUseCase {
    private final UserGateway userGateway;
    private final RoleGateway roleGateway;

    public CreateClientUseCase(UserGateway userGateway, RoleGateway roleGateway) {
        this.userGateway = userGateway;
        this.roleGateway = roleGateway;
    }

    public UserDomain execute(UserDomain userDomain) {
        if (userGateway.existsByCpf(userDomain.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }

        if (userGateway.existsByEmailIgnoreCase(userDomain.getEmail())) {
            throw new ConflictException("E-mail já cadastrado");
        }

        if (userGateway.existsByPhone(userDomain.getPhone())) {
            throw new ConflictException("Telefone já cadastrado");
        }

        RoleDomain roleDomain = roleGateway.findById(2).orElseThrow(() -> new ConflictException("Função não encontrada"));

        userDomain.setId(null);
        userDomain.setCreatedAt(LocalDateTime.now());
        userDomain.setUpdatedAt(LocalDateTime.now());
        userDomain.setRoleDomain(roleDomain);
        return userGateway.save(userDomain);
    }
}