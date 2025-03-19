package sptech.school.projetoPI.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.entities.User;
import sptech.school.projetoPI.exceptions.EntityConflictException;
import sptech.school.projetoPI.exceptions.EntityNotFoundException;
import sptech.school.projetoPI.repositories.EmployeeRepository;
import sptech.school.projetoPI.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final EmployeeRepository employeeRepository;

    public UserService(UserRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    public User signUser(User user) {
        List<String> entidadesJaRegistradas = new ArrayList<>();

        if(repository.existsByCpf(user.getCpf()) || employeeRepository.existsByCpf(user.getCpf())) {
            entidadesJaRegistradas.add("CPF");
        }
        if(repository.existsByEmailIgnoreCase(user.getEmail()) || employeeRepository.existsByEmailIgnoreCase(user.getEmail())) {
            entidadesJaRegistradas.add("E-mail");
        }
        if(repository.existsByPhone(user.getPhone()) || employeeRepository.existsByPhone(user.getPhone())) {
            entidadesJaRegistradas.add("Telefone");
        }

        if(!entidadesJaRegistradas.isEmpty()) {
            throw new EntityConflictException(
                    "Os seguintes itens já foram registrados: " + entidadesJaRegistradas
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

        List<String> entidadesJaRegistradas = new ArrayList<>();

        if(repository.existsByIdNotAndCpf(id, user.getCpf()) || employeeRepository.existsByCpf(user.getCpf())) {
            entidadesJaRegistradas.add("CPF");
        }
        if(repository.existsByIdNotAndEmailIgnoreCase(id, user.getEmail()) || employeeRepository.existsByEmailIgnoreCase(user.getEmail())) {
            entidadesJaRegistradas.add("E-mail");
        }
        if(repository.existsByIdNotAndPhone(id, user.getPhone()) || employeeRepository.existsByPhone(user.getPhone())) {
            entidadesJaRegistradas.add("Telefone");
        }

        if(!entidadesJaRegistradas.isEmpty()) {
            throw new EntityConflictException(
                    "Os seguintes itens já foram registrados: " + entidadesJaRegistradas
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

        repository.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}