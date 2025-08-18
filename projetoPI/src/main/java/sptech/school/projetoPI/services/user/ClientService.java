package sptech.school.projetoPI.services.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.*;
import sptech.school.projetoPI.core.enums.Logs;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.infrastructure.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.services.AbstractService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService extends AbstractService<Client> {
    private final ClientRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final FeedbackRepository feedbackRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Client postMethod(Client client) {
        userService.validateUniqueProperties(client.getCpf(), client.getEmail(), client.getPhone());

        String encodedPassword = passwordEncoder.encode(client.getPassword());

        client.setId(null);
        client.setPassword(encodedPassword);
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        log.info(Logs.POST_SUCCESSFULLY.getMessage());
        return repository.save(client);
    }

    @Override
    public List<Client> getAllMethod() {
        if (repository.findAllByActiveTrue().isEmpty()) log.info(Logs.GET_ALL_SUCCESSFULLY_EMPTY.getMessage());
        else log.info(Logs.GET_ALL_SUCCESSFULLY.getMessage());

        return repository.findAllByActiveTrue();
    }

    @Override
    public Client getByIdMethod(Integer id) {
        return repository.findByIdAndActiveTrue(id)
            .map((entity) -> {
                log.info(Logs.GET_BY_ID_SUCCESSFULLY.getMessage().formatted(id));
                return entity;
            })
            .orElseThrow(() -> {
                log.error(Logs.GET_BY_ID_NOT_FOUND.getMessage().formatted(id));
                return new EntityNotFoundException(
                        Logs.GET_BY_ID_NOT_FOUND.getMessage().formatted(id)
                );
            });
    }

    @Override
    public Client putByIdMethod(Client client, Integer id) {
        if (!repository.existsById(id)) {
            log.error(Logs.PUT_NOT_FOUND.getMessage().formatted(id));
            throw new EntityNotFoundException(
                    Logs.PUT_NOT_FOUND.getMessage().formatted(id)
            );
        }

        if(repository.existsByIdAndActiveFalse(id)) {
            log.error(Logs.PUT_INACTIVE_ENTITY.getMessage().formatted(id));
            throw new InactiveEntityException(
                    Logs.PUT_INACTIVE_ENTITY.getMessage().formatted(id)
            );
        }

        userService.validateUniquePropertiesOnUpdate(id, client.getCpf(), client.getEmail(), client.getPhone(), true);

        client.setId(id);
        client.setCreatedAt(repository.findById(id).get().getCreatedAt());
        client.setUpdatedAt(LocalDateTime.now());
        log.info(Logs.PUT_SUCCESSFULLY.getMessage().formatted(id));
        return repository.save(client);
    }

    @Override
    public void deleteByIdMethod(Integer id) {
        if (!repository.existsById(id)) {
            log.error(Logs.DELETE_NOT_FOUND.getMessage().formatted(id));
            throw new EntityNotFoundException(
                    Logs.DELETE_NOT_FOUND.getMessage().formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            log.error(Logs.DELETE_INACTIVE_ENTITY.getMessage().formatted(id));
            throw new InactiveEntityException(
                    Logs.DELETE_INACTIVE_ENTITY.getMessage().formatted(id)
            );
        }

        if (scheduleRepository.existsByClientId(id)) {
            log.error(Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Schedules", "Client"));
            throw new ForeignKeyConstraintException(
                    Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Schedules", "Client")
            );
        }

        if (feedbackRepository.existsByClientId(id)) {
            log.error(Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Feedbacks", "Client"));
            throw new ForeignKeyConstraintException(
                    Logs.DELETE_FOREIGN_KEY_CONFLICT.getMessage().formatted("Feedbacks", "Client")
            );
        }

        Client client = repository.findById(id).get();
        client.setActive(false);
        client.setUpdatedAt(LocalDateTime.now());
        repository.save(client);
        log.info(Logs.DELETE_SUCCESSFULLY.getMessage().formatted(id));
    }
}
