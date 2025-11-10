package sptech.school.projetoPI.infrastructure.config.auth;

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
import sptech.school.projetoPI.core.application.dto.login.UserDetailsDto;
import sptech.school.projetoPI.core.application.dto.login.UserLoginDto;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.UserGateway;
import sptech.school.projetoPI.infrastructure.mappers.UserMapper;
import sptech.school.projetoPI.core.application.dto.login.UserTokenDto;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserGateway userGateway;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userGateway.findByEmail(username)
                .map(UserDetailsDto::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public UserTokenDto autenticar(UserLoginDto loginDto, AuthenticationManager authenticationManager) {
        UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getSenha());

        Authentication auth = authenticationManager.authenticate(credentials);
        SecurityContextHolder.getContext().setAuthentication(auth);

        UserDetailsDto userDetails = (UserDetailsDto) auth.getPrincipal();

        String token = jwtService.generateToken(
                userDetails.getUsername(), // email
                userDetails.getRoleName(), // nome da role
                userDetails.getId()
        );

        UserDomain user = userGateway.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new ResponseStatusException(404, "Email não encontrado", null));

        return UserMapper.of(user, token);
    }
}
