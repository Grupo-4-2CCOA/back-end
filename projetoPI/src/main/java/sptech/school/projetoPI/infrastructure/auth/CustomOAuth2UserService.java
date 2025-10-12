package sptech.school.projetoPI.infrastructure.auth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import sptech.school.projetoPI.core.application.usecases.exceptions.ConflictException;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.core.domains.UserDomain;
import sptech.school.projetoPI.core.gateways.RoleGateway;
import sptech.school.projetoPI.core.gateways.UserGateway;
import sptech.school.projetoPI.infrastructure.mappers.RoleMapper;
import sptech.school.projetoPI.infrastructure.persistence.entity.RoleJpaEntity;
import sptech.school.projetoPI.infrastructure.persistence.repositories.JpaRoleRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final UserGateway userGateway;
    private final RoleGateway roleGateway;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        logger.info("Processando login com Google para o email: {}", email);

        RoleDomain roleDomain = roleGateway.findById(2).orElseThrow(() -> new ConflictException("Função não encontrada"));

        // Busca o usuário ou cria um novo se não existir
        UserDomain user = userGateway.findByEmail(email).orElseGet(() -> {
            logger.info("Email {} não encontrado. Criando novo usuário padrão (USER).", email);
            UserDomain newUser = new UserDomain();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setRoleDomain(roleDomain);
            newUser.setActive(true);
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setUpdatedAt(LocalDateTime.now());
            return userGateway.save(newUser);
        });

        logger.info("Usuário {} autenticado com o papel: {}", email, user.getRoleDomain());

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("role", user.getRoleDomain());
        attributes.put("id", user.getId());

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRoleDomain()));

        return new DefaultOAuth2User(authorities, attributes, "email");
    }
}