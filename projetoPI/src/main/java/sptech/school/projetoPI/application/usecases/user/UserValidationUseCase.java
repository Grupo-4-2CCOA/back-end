package sptech.school.projetoPI.application.usecases.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.infrastructure.exceptions.ConflictException;

@Service
@RequiredArgsConstructor
public class UserValidationUseCase {

    private final ClientGateway clientGateway;

    // Use Case para validar propriedades únicas na criação
    public void validateUniqueProperties(String cpf, String email, String phone) {
        if (clientGateway.existsByCpf(cpf)) {
            throw new ConflictException("CPF já cadastrado");
        }
        if (clientGateway.existsByEmailIgnoreCase(email)) {
            throw new ConflictException("E-mail já cadastrado");
        }
        if (clientGateway.existsByPhone(phone)) {
            throw new ConflictException("Telefone já cadastrado");
        }
    }

    // Use Case para validar propriedades únicas na atualização
    public void validateUniquePropertiesOnUpdate(Integer id, String cpf, String email, String phone) {
        if (clientGateway.existsByIdNotAndCpf(id, cpf)) {
            throw new ConflictException("CPF já cadastrado para outro usuário");
        }
        if (clientGateway.existsByIdNotAndEmailIgnoreCase(id, email)) {
            throw new ConflictException("E-mail já cadastrado para outro usuário");
        }
        if (clientGateway.existsByIdNotAndPhone(id, phone)) {
            throw new ConflictException("Telefone já cadastrado para outro usuário");
        }
    }
}