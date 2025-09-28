package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sptech.school.projetoPI.old.core.application.usecases.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.repositories.EmployeeRepository;
import sptech.school.projetoPI.services.user.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Cadastro de Usuários com mesmo CPF deve estourar EntityConflictException")
    void executeUserWithDuplicateCpfMustThrowEntityConflictExceptionTest() {
        String cpf = "55522299915";

        when(clientRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.validateUniqueProperties(cpf, null, null));
    }

    @Test
    @DisplayName("Cadastro de Usuários com mesmo E-mail deve estourar EntityConflictException")
    void executeUserWithDuplicateEmailMustThrowEntityConflictExceptionTest() {
        String email = "fabmigmurnagenz@gmail.com";

        when(employeeRepository.existsByEmailIgnoreCase(anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.validateUniqueProperties(null, email, null));
    }

    @Test
    @DisplayName("Cadastro de Usuários com mesmo telefone deve estourar EntityConflictException")
    void executeUserWithDuplicatePhoneMustThrowEntityConflictExceptionTest() {
        String phone = "11999998888";

        when(clientRepository.existsByPhone(anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.validateUniqueProperties(null, null, phone));
    }

    @Test
    @DisplayName("Atualização de Client no Update com CPF já registrado deve estourar EntityConflictException")
    void executeClientWithDuplicateCpfOnUpdateMustThrowEntityConflictExceptionTest() {
        String cpf = "55522299915";

        when(clientRepository.existsByIdNotAndCpf(anyInt(), anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.validateUniquePropertiesOnUpdate(1, cpf, null, null, true));
    }

    @Test
    @DisplayName("Atualização de Client no Update com E-mail já registrado deve estourar EntityConflictException")
    void executeClientWithDuplicateEmailOnUpdateMustThrowEntityConflictExceptionTest() {
        String email = "fabmigmurnagenz@gmail.com";

        when(clientRepository.existsByIdNotAndEmailIgnoreCase(anyInt(), anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.validateUniquePropertiesOnUpdate(1, null, email, null, true));
    }

    @Test
    @DisplayName("Atualização de Client no Update com telefone já registrado deve estourar EntityConflictException")
    void executeClientWithDuplicatePhoneOnUpdateMustThrowEntityConflictExceptionTest() {
        String phone = "11999998888";

        when(clientRepository.existsByIdNotAndPhone(anyInt(), anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.validateUniquePropertiesOnUpdate(1, null, null, phone, true));
    }

    @Test
    @DisplayName("Atualização de Employee no Update com CPF já registrado deve estourar EntityConflictException")
    void executeEmployeeWithDuplicateCpfOnUpdateMustThrowEntityConflictExceptionTest() {
        String cpf = "55522299915";

        when(employeeRepository.existsByIdNotAndCpf(anyInt(), anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.validateUniquePropertiesOnUpdate(1, cpf, null, null, false));
    }

    @Test
    @DisplayName("Atualização de Employee no Update com E-mail já registrado deve estourar EntityConflictException")
    void executeEmployeeWithDuplicateEmailOnUpdateMustThrowEntityConflictExceptionTest() {
        String email = "fabmigmurnagenz@gmail.com";

        when(employeeRepository.existsByIdNotAndEmailIgnoreCase(anyInt(), anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.validateUniquePropertiesOnUpdate(1, null, email, null, false));
    }

    @Test
    @DisplayName("Atualização de Employee no Update com telefone já registrado deve estourar EntityConflictException")
    void executeEmployeeWithDuplicatePhoneOnUpdateMustThrowEntityConflictExceptionTest() {
        String phone = "11999998888";

        when(employeeRepository.existsByIdNotAndPhone(anyInt(), anyString())).thenReturn(true);

        assertThrows(EntityConflictException.class, () -> service.validateUniquePropertiesOnUpdate(1, null, null, phone, false));
    }
}