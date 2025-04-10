package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.entities.User;
import sptech.school.projetoPI.enums.Role;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.repositories.FeedbackRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;
import sptech.school.projetoPI.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final FeedbackRepository feedbackRepository;

    public UserService(UserRepository repository, ScheduleRepository scheduleRepository, FeedbackRepository feedbackRepository) {
        this.repository = repository;
        this.scheduleRepository = scheduleRepository;
        this.feedbackRepository = feedbackRepository;
    }

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

        if (user.getRole().equals(Role.OWNER) && repository.existsByRole(Role.OWNER)) {
            throw new EntityConflictException(
                    "Já existe um usuário como 'dono'"
            );
        }

        user.setId(null);
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Integer id) {
        return repository.findById(id).orElseThrow(
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
        return repository.save(user);
    }

    public ResponseEntity<Void> deleteUserById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O usuário de ID %d não foi encontrado".formatted(id)
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

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}