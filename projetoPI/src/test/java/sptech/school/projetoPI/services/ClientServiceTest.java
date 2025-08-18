package sptech.school.projetoPI.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.InactiveEntityException;
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
    @DisplayName("Quando método postMethod() for chamado com credenciais válidas, deve retornar Client")
    void executeEntitySignWithValidParametersTest() {
        when(repository.save(client)).thenReturn(client);

        Client response = service.postMethod(client);
        assertEquals(client, response);
    }

    @Override
    @Test
    @DisplayName("Quando existir 3 Clients na lista, método getAllMethod() deve retornar tamanho 3")
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

        List<Client> response = service.getAllMethod();
        assertEquals(3, response.size());
    }

    @Override
    @Test
    @DisplayName("Quando existir Client com ID 1, método getByIdMethod() deve retornar o Client encontrado")
    void executeEntityFindByIdMustReturnEntityWithIdOneTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.of(client));

        Client response = service.getByIdMethod(1);
        assertEquals(client, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Client com ID requisitado, método getByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityFindByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.findByIdAndActiveTrue(anyInt())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> service.getByIdMethod(1));
    }

    @Override
    @Test
    @DisplayName("Quando método putByIdMethod() for chamado com credenciais válidas, deve retornar Client atualizado")
    void executeEntityPutByIdWithValidEntityMustReturnUpdatedEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(new Client()));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(repository.save(client)).thenReturn(client);

        Client response = service.putByIdMethod(client, anyInt());
        assertEquals(client, response);
    }

    @Override
    @Test
    @DisplayName("Quando não existir Client com ID requisitado, método putByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityPutByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.putByIdMethod(client, 1));
    }

    @Test
    @DisplayName("Quando Client com ID requisitado estiver inativo, método putByIdMethod() deve estourar InactiveEntityException")
    void executeClientPutByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.putByIdMethod(client, 1));
    }

    @Override
    @Test
    @DisplayName("Quando método deleteByIdMethod() for chamado com ID válido, deve inativar entidade")
    void executeEntityDeleteByIdWithValidIdMustInactiveOrDeleteEntityTest() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(client));
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByClientId(anyInt())).thenReturn(false);
        when(feedbackRepository.existsByClientId(anyInt())).thenReturn(false);

        service.deleteByIdMethod(1);

        assertFalse(client.getActive());
    }

    @Override
    @Test
    @DisplayName("Quando não existir Client com ID requisitado, método deleteByIdMethod() deve estourar EntityNotFoundException")
    void executeEntityDeleteByIdWithInvalidIdMustThrowEntityNotFoundExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Client com ID requisitado estiver inativo, método deleteByIdMethod() deve estourar InactiveEntityException")
    void executeClientDeleteByIdWithInactiveEntityMustThrowInactiveEntityExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(true);
        assertThrows(InactiveEntityException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Client com ID requisitado estiver registrado em um Schedule, método deleteByIdMethod() deve estourar ForeignKeyConstraintException")
    void executeClientDeleteByIdWithScheduleForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByClientId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteByIdMethod(1));
    }

    @Test
    @DisplayName("Quando Client com ID requisitado estiver registrado em um Feedback, método deleteByIdMethod() deve estourar ForeignKeyConstraintException")
    void executeClientDeleteByIdWithFeedbackForeignKeyConstraintMustThrowForeignKeyConstraintExceptionTest() {
        when(repository.existsById(anyInt())).thenReturn(true);
        when(repository.existsByIdAndActiveFalse(anyInt())).thenReturn(false);
        when(scheduleRepository.existsByClientId(anyInt())).thenReturn(false);
        when(feedbackRepository.existsByClientId(anyInt())).thenReturn(true);
        assertThrows(ForeignKeyConstraintException.class, () -> service.deleteByIdMethod(1));
    }
}