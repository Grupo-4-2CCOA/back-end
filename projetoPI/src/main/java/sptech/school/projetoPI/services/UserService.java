package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.entities.User;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.FeedbackRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;
import sptech.school.projetoPI.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final FeedbackRepository feedbackRepository;

    public User signUser(User user) {
        if (repository.existsByCpf(user.getCpf())) {
            throw new EntityConflictException(
                    "Já existe um usuário cadastrado com este CPF"
            );
        }

        if (repository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new EntityConflictException(
                    "Já existe um usuário cadastrado com este E-mail"
            );
        }

        if (repository.existsByPhone(user.getPhone())) {
            throw new EntityConflictException(
                    "Já existe um usuário cadastrado com este Telefone"
            );
        }

        if (user.getRole().getName().equals("OWNER") && repository.existsByRoleName("OWNER")) {
            throw new EntityConflictException(
                    "Já existe um usuário como 'dono'"
            );
        }

        user.setId(null);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAllByActiveTrue();
    }

    public User getUserById(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O usuário de ID %d não foi encontrado".formatted(id)
                )
        );
    }

    public User updateUserById(User user, Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O usuário com o ID %d não foi encontrado".formatted(id)
            );
        }

        if(repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O usuário com o ID %d está inativo".formatted(id)
            );
        }

        if (repository.existsByIdNotAndCpf(id, user.getCpf())) {
            throw new EntityConflictException(
                    "Já existe um usuário cadastrado com este CPF"
            );
        }

        if (repository.existsByIdNotAndEmailIgnoreCase(id, user.getEmail())) {
            throw new EntityConflictException(
                    "Já existe um usuário cadastrado com este E-mail"
            );
        }

        if (repository.existsByIdNotAndPhone(id, user.getPhone())) {
            throw new EntityConflictException(
                    "Já existe um usuário cadastrado com este Telefone"
            );
        }

        user.setId(id);
        user.setCreatedAt(repository.findById(id).get().getCreatedAt());
        user.setUpdatedAt(LocalDateTime.now());
        return repository.save(user);
    }

    public void deleteUserById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O usuário de ID %d não foi encontrado".formatted(id)
            );
        }

        if (repository.existsByIdAndActiveFalse(id)) {
            throw new InactiveEntityException(
                    "O usuário com o ID %d já está inativo".formatted(id)
            );
        }

        if (scheduleRepository.existsByUserId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes agendamentos estão relacionados com este usuário: %s".formatted(scheduleRepository.findAllByUserId(id)
                            .stream().map(Schedule::getId).toList())
            );
        }

        if (feedbackRepository.existsByUserId(id)) {
            throw new ForeignKeyConstraintException(
                    "Os seguintes feedbacks estão relacionados com este usuário: %s".formatted(feedbackRepository.findAllByUserId(id)
                            .stream().map(Feedback::getId).toList())
            );
        }

        User user = repository.findById(id).get();
        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);
    }
}