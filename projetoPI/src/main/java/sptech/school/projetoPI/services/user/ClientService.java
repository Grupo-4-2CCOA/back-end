package sptech.school.projetoPI.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.projetoPI.config.GerenciadorTokenJwt;
import sptech.school.projetoPI.dto.login.UserMapper;
import sptech.school.projetoPI.dto.login.UserTokenDto;
import sptech.school.projetoPI.entities.*;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.ClientRepository;
import sptech.school.projetoPI.repositories.FeedbackRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public Client signClient(Client client) {
        userService.validateUniqueProperties(client.getCpf(), client.getEmail(), client.getPhone());

        String senhaCriptografada = passwordEncoder.encode(client.getPassword());

        client.setId(null);
        client.setPassword(senhaCriptografada);
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        return repository.save(client);
    }

    public List<Client> getAllClients() {
        return repository.findAllByActiveTrue();
    }

    public Client getClientById(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O cliente de ID %d não foi encontrado".formatted(id)
                )
        );
    }

    public Client updateClientById(Client client, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O cliente com o ID %d não foi encontrado".formatted(id)
            );
        }

        if(repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O cliente com o ID %d está inativo".formatted(id)
            );
        }

        userService.validateUniquePropertiesOnUpdate(id, client.getCpf(), client.getEmail(), client.getPhone(), true);
        client.setId(id);
        client.setCreatedAt(repository.findById(id).get().getCreatedAt());
        client.setUpdatedAt(LocalDateTime.now());
        return repository.save(client);
    }

    public void deleteClientById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O cliente de ID %d não foi encontrado".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O cliente com o ID %d já está inativo".formatted(id)
            );
        }

        if (scheduleRepository.existsByClientId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes agendamentos estão relacionados com este cliente: %s".formatted(scheduleRepository.findAllByClientId(id)
                            .stream().map(Schedule::getId).toList())
            );
        }

        if (feedbackRepository.existsByClientId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes feedbacks estão relacionados com este cliente: %s".formatted(feedbackRepository.findAllByClientId(id)
                            .stream().map(Feedback::getId).toList())
            );
        }

        Client client = repository.findById(id).get();
        client.setActive(false);
        client.setUpdatedAt(LocalDateTime.now());
        repository.save(client);
    }
}
