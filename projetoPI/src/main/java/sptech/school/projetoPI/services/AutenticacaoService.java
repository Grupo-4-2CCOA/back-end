package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.dto.user.UserDetailsDto;
import sptech.school.projetoPI.entities.User;
import sptech.school.projetoPI.repositories.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOpt = repository.findByEmail(username);

        if(userOpt.isEmpty()){
            throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
        }

        return new UserDetailsDto(userOpt.get());
    }
}
