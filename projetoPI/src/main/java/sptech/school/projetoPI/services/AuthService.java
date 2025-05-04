package sptech.school.projetoPI.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.school.projetoPI.config.GerenciadorTokenJwt;
import sptech.school.projetoPI.dto.login.UserDetailsDto;
import sptech.school.projetoPI.dto.login.UserLoginDto;
import sptech.school.projetoPI.dto.login.UserMapper;
import sptech.school.projetoPI.dto.login.UserTokenDto;
import sptech.school.projetoPI.repositories.ClientRepository;
import sptech.school.projetoPI.repositories.EmployeeRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return clientRepository.findByEmail(username)
                .map(UserDetailsDto::new)
                .or(() -> employeeRepository.findByEmail(username).map(UserDetailsDto::new))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public UserTokenDto autenticar(UserLoginDto loginDto, AuthenticationManager authenticationManager) {

        UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getSenha());

        Authentication auth = authenticationManager.authenticate(credentials);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = gerenciadorTokenJwt.generateToken(auth);

        return clientRepository.findByEmail(loginDto.getEmail())
                .map(client -> UserMapper.of(client, token))
                .orElseGet(() -> employeeRepository.findByEmail(loginDto.getEmail())
                        .map(employee -> UserMapper.of(employee, token))
                        .orElseThrow(() -> new ResponseStatusException(404, "Email não encontrado", null)));
    }

}
