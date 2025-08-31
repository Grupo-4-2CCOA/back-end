package sptech.school.projetoPI.services.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.repositories.EmployeeRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    public void validateUniqueProperties(String cpf, String email, String phone) {
        if(cpfExists(cpf)) {
            log.error(Logs.POST_CPF_CONFLICT.getMessage().formatted(cpf));
            throw new EntityConflictException(
                    Logs.POST_CPF_CONFLICT.getMessage().formatted(cpf)
            );
        }

        if(emailExists(email)) {
            log.error(Logs.POST_EMAIL_CONFLICT.getMessage().formatted(email));
            throw new EntityConflictException(
                    Logs.POST_EMAIL_CONFLICT.getMessage().formatted(email)
            );
        }

        if(phoneExists(phone)) {
            log.error(Logs.POST_PHONE_CONFLICT.getMessage().formatted(phone));
            throw new EntityConflictException(
                    Logs.POST_PHONE_CONFLICT.getMessage().formatted(phone)
            );
        }
    }

    public void validateUniquePropertiesOnUpdate(Integer id, String cpf, String email, String phone, boolean isClient) {
        if(cpfExists(id, cpf, isClient)) {
            log.error(Logs.PUT_CPF_CONFLICT.getMessage().formatted(cpf));
            throw new EntityConflictException(
                    Logs.PUT_CPF_CONFLICT.getMessage().formatted(cpf)
            );
        }

        if(emailExists(id, email, isClient)) {
            log.error(Logs.PUT_EMAIL_CONFLICT.getMessage().formatted(email));
            throw new EntityConflictException(
                    Logs.PUT_EMAIL_CONFLICT.getMessage().formatted(email)
            );
        }

        if(phoneExists(id, phone, isClient)) {
            log.error(Logs.PUT_PHONE_CONFLICT.getMessage().formatted(phone));
            throw new EntityConflictException(
                    Logs.PUT_PHONE_CONFLICT.getMessage().formatted(phone)
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
