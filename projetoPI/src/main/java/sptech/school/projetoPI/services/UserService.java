package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.projetoPI.config.GerenciadorTokenJwt;
import sptech.school.projetoPI.dto.client.ClientMapper;
import sptech.school.projetoPI.dto.client.ClientTokenDto;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.entities.Feedback;
import sptech.school.projetoPI.entities.Schedule;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityConflictException;
import sptech.school.projetoPI.exceptions.exceptionClass.EntityNotFoundException;
import sptech.school.projetoPI.exceptions.exceptionClass.ForeignKeyConstraintException;
import sptech.school.projetoPI.exceptions.exceptionClass.InactiveEntityException;
import sptech.school.projetoPI.repositories.ClienteRepository;
import sptech.school.projetoPI.repositories.FeedbackRepository;
import sptech.school.projetoPI.repositories.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ClienteRepository repository;
    private final ScheduleRepository scheduleRepository;
    private final FeedbackRepository feedbackRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final GerenciadorTokenJwt genenciadorTokenJwt;

    public Client signUser(Client user) {
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

        String senhaCriptografada = passwordEncoder.encode(user.getPassword());

        user.setId(null);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(senhaCriptografada);
        return repository.save(user);
    }

    public ClientTokenDto autenticar(Client user){
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                user.getEmail(), user.getPassword()
        );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Client userAutenticado = repository.findByEmail(user.getEmail())
                .orElseThrow(
                        () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = genenciadorTokenJwt.generateToken(authentication);


        return ClientMapper.of(userAutenticado, token);

    }

    public List<Client> getAllUsers() {
        return repository.findAllByActiveTrue();
    }

    public Client getUserById(Integer id) {
        return repository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "O usuário de ID %d não foi encontrado".formatted(id)
                )
        );
    }

    public Client updateUserById(Client user, Integer id) {
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

//        if (scheduleRepository.existsByUserId(id)) {
//            throw new ForeignKeyConstraintException(
//                    "Os seguintes agendamentos estão relacionados com este usuário: %s".formatted(scheduleRepository.findAllByUserId(id)
//                            .stream().map(Schedule::getId).toList())
//            );
//        }
//
//        if (feedbackRepository.existsByUserId(id)) {
//            throw new ForeignKeyConstraintException(
//                    "Os seguintes feedbacks estão relacionados com este usuário: %s".formatted(feedbackRepository.findAllByUserId(id)
//                            .stream().map(Feedback::getId).toList())
//            );
//        }

        Client user = repository.findById(id).get();
        user.setActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        repository.save(user);
    }
}