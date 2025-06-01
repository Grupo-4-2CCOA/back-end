package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.ClientRepository;
import sptech.school.projetoPI.repositories.FeedbackRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;
import sptech.school.projetoPI.services.user.ClientService;
import sptech.school.projetoPI.services.user.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ClientServiceTest extends ServiceTest {

    @InjectMocks
    private ClientService service;

    @Mock
    private ClientRepository repository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    private final Client client = Client.builder()
            .id(1)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .active(true)
            .name("Fabricio")
            .cpf("55522299915")
            .email("fabricio@gmail.com")
            .phone("11989898989")
            .password("123456789")
            .cep("01234100")
            .build();

    @Override
    @Test
    @DisplayName("Quando método SignClient() for chamado com credenciais válidas, deve retornar Client")
    void executeEntitySignWithValidParametersTest() {
        when(repository.save(client)).thenReturn(client);

        Client response = service.signClient(client);
        assertEquals(client, response);
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Clients na lista, método GetAllClients() deve retornar tamanho 3")
    void executeEntityFindAllWithThreeEntitiesMustReturnThreeTest() {
        List<Client> clients = List.of(
                Client.builder()
                        .id(1)
                        .name("Fabricio")
                        .cpf("55522299915")
                        .email("fabricio@gmail.com")
                        .build(),

                Client.builder()
                        .id(2)
                        .name("Miguel")
                        .cpf("11122233345")
                        .email("miguel@gmail.com")
                        .build(),

                Client.builder()
                        .id(3)
                        .name("Martinez")
                        .cpf("88822244432")
                        .email("martinez@gmail.com")
                        .build()
        );

        when(repository.findAllByActiveTrue()).thenReturn(clients);

        List<Client> response = service.getAllClients();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Client com ID 1, método GetClientById() deve retornar o Client encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(client));

        Client response = service.getClientById(1);
        assertEquals(client, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Client com ID requisitado, método GetClientById() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getClientById(1));
    }

    @Override
    @Test
    @DisplayName("Quando método UpdateClientById() for chamado com credenciais válidas, deve retornar Client atualizado")
    void executeEntityUpdateByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Client()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.save(client)).thenReturn(client);

        Client response = service.updateClientById(client, anyInt());
        assertEquals(client, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Client com ID requisitado, método UpdateClientById() deve estourar EntityNotFoundException")
    void executeEntityUpdateByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.updateClientById(client, 1));
    }

    @Test
    @DisplayName("Quando Client com ID requisitado estiver inativo, método UpdateClientById() deve estourar InactiveEntityException")
    void executeClientUpdateByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.updateClientById(client, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método DeleteClientById() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(client));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByClientId(anyInt())).thenReturn(false);
        when(feedbackRepository.existsByClientId(anyInt())).thenReturn(false);

        service.deleteClientById(1);

        assertFalse(client.getActive());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Client com ID requisitado, método DeleteClientById() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteClientById(1));
    }

    @Test
    @DisplayName("Quando Client com ID requisitado estiver inativo, método DeleteClientById() deve estourar InactiveEntityException")
    void executeClientDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteClientById(1));
    }

    @Test
    @DisplayName("Quando Client com ID requisitado estiver registrado em um Schedule, método DeleteClientById() deve estourar ForeignKeyConstraintException")
    void executeClientDeleteByIdWithScheduleForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByClientId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteClientById(1));
    }

    @Test
    @DisplayName("Quando Client com ID requisitado estiver registrado em um Feedback, método DeleteClientById() deve estourar ForeignKeyConstraintException")
    void executeClientDeleteByIdWithFeedbackForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByClientId(anyInt())).thenReturn(false);
        when(feedbackRepository.existsByClientId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteClientById(1));
    }
}