package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.User;
import sptech.school.projetoPI.exceptions.EntityConflictException;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User signUser(User user) {
        if (repository.existsByCpfOrEmailIgnoreCase(user.getCpf(), user.getEmail())) {
            throw new EntityConflictException(
                    "Este email ou CPF já está cadastrado no banco."
            );
        }

        return repository.save(user);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getUserById(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O usuário de id %d não foi encontrado.".formatted(id)
                )
        );
    }

    public User updateUserById(User user, Integer id) {
        // Não sei como fazer esse.
        return null;
    }

    // Não sei se isso tá certo..
    public ResponseEntity<Void> deleteUserById(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(
                    "O usuário de id %d não foi encontrado.".formatted(id)
            );
        }

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
