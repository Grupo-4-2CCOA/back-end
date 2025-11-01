package sptech.school.projetoPI.infrastructure.config.auth;

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
import sptech.school.projetoPI.core.domains.ClientDomain;
import sptech.school.projetoPI.core.domains.EmployeeDomain;
import sptech.school.projetoPI.core.gateways.ClientGateway;
import sptech.school.projetoPI.core.gateways.EmployeeGateway;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final EmployeeGateway employeeGateway;
    private final ClientGateway clientGateway;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String role;
        Integer clientId = null;

        logger.info("Processando login com Google para o email: {}", email);

        Optional<EmployeeDomain> employeeOptional = employeeGateway.findByEmail(email);
        if (employeeOptional.isPresent()) {
            EmployeeDomain employeeDomain = employeeOptional.get();
            if (employeeDomain.getRole() != null && "OWNER".equals(employeeDomain.getRole().getName())) {
                role = "ADMIN";
            } else {
                role = "FUNC";
            }
        } else {
            ClientDomain clientDomain = clientGateway.findByEmail(email).orElseGet(() -> {
                logger.info("Email {} não encontrado. Criando novo cliente.", email);
                ClientDomain newClientDomain = new ClientDomain();
                newClientDomain.setCreatedAt(LocalDateTime.now());
                newClientDomain.setUpdatedAt(LocalDateTime.now());
                newClientDomain.setActive(true);
                newClientDomain.setEmail(email);
                newClientDomain.setName(name);
                return clientGateway.save(newClientDomain);
            });
            role = "USER";
            clientId = clientDomain.getId();
        }

        logger.info("Usuário {} autenticado com o papel: {} (clientId: {})", email, role, clientId);

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("role", role);
        if (clientId != null) {
            attributes.put("id", clientId);
        }

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

        return new DefaultOAuth2User(authorities, attributes, "email");
    }
}