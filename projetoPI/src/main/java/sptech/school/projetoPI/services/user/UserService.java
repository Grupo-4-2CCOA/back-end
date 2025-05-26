package sptech.school.projetoPI.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.repositories.ClientRepository;
import sptech.school.projetoPI.repositories.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    public void validateUniqueProperties(String cpf, String email, String phone) {
        if(cpfExists(cpf)) {
            throw new EntityConflictException(
                    "Já existe um usuário com este CPF"
            );
        }

        if(emailExists(email)) {
            throw new EntityConflictException(
                    "Já existe um usuário com este E-mail"
            );
        }

        if(phoneExists(phone)) {
            throw new EntityConflictException(
                    "Já existe um usuário com este número de telefone"
            );
        }
    }

    public void validateUniquePropertiesOnUpdate(Integer id, String cpf, String email, String phone, boolean isClient) {
        if(cpfExists(id, cpf, isClient)) {
            throw new EntityConflictException(
                    "Já existe um usuário com este CPF"
            );
        }

        if(emailExists(id, email, isClient)) {
            throw new EntityConflictException(
                    "Já existe um usuário com este E-mail"
            );
        }

        if(phoneExists(id, phone, isClient)) {
            throw new EntityConflictException(
                    "Já existe um usuário com este número de telefone"
            );
        }
    }

    public boolean cpfExists(String cpf) {
        return clientRepository.existsByCpf(cpf) || employeeRepository.existsByCpf(cpf);
    }

    public boolean emailExists(String email) {
        return clientRepository.existsByEmailIgnoreCase(email) || employeeRepository.existsByEmailIgnoreCase(email);
    }

    public boolean phoneExists(String phone) {
        return clientRepository.existsByPhone(phone) || employeeRepository.existsByPhone(phone);
    }

    public boolean cpfExists(Integer id, String cpf, boolean isClient) {
        return isClient?
                clientRepository.existsByIdNotAndCpf(id, cpf) || employeeRepository.existsByCpf(cpf) :
                clientRepository.existsByCpf(cpf) || employeeRepository.existsByIdNotAndCpf(id, cpf);
    }

    public boolean emailExists(Integer id, String email, boolean isClient) {
        return isClient?
                clientRepository.existsByIdNotAndEmailIgnoreCase(id, email) || employeeRepository.existsByEmailIgnoreCase(email) :
                clientRepository.existsByEmailIgnoreCase(email) || employeeRepository.existsByIdNotAndEmailIgnoreCase(id, email);
    }

    public boolean phoneExists(Integer id, String phone, boolean isClient) {
        return isClient?
                clientRepository.existsByIdNotAndPhone(id, phone) || employeeRepository.existsByPhone(phone) :
                clientRepository.existsByPhone(phone) || employeeRepository.existsByIdNotAndPhone(id, phone);
    }
}
