package sptech.school.projetoPI.services;

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
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.entities.Employee;
import sptech.school.projetoPI.repositories.ClientRepository;
import sptech.school.projetoPI.repositories.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final EmployeeRepository employeeRepository;
    private final ClientRepository clientRepository;

    public CustomOAuth2UserService(EmployeeRepository employeeRepository, ClientRepository clientRepository) {
        this.employeeRepository = employeeRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String role;

        logger.info("Processando login com Google para o email: {}", email);

        // 1. Verifica se é um funcionário
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(email);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            // Mapeia o papel do funcionário para as roles do sistema
            if (employee.getRole() != null && "OWNER".equals(employee.getRole().getName())) {
                role = "ADMIN";
            } else {
                role = "FUNC";
            }
        } else {
            // 2. Se não for funcionário, verifica se é um cliente existente ou cria um novo
            clientRepository.findByEmail(email).orElseGet(() -> {
                logger.info("Email {} não encontrado. Criando novo cliente.", email);
                Client newClient = new Client();
                newClient.setCreatedAt(LocalDateTime.now());
                newClient.setUpdatedAt(LocalDateTime.now());
                newClient.setActive(true);
                newClient.setEmail(email);
                newClient.setName(name);
                return clientRepository.save(newClient);
            });
            role = "USER";
        }

        logger.info("Usuário {} autenticado com o papel: {}", email, role);

        // Adiciona a role aos atributos do usuário para que o controller possa acessá-la
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        attributes.put("role", role);

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));

        // Retorna um novo OAuth2User com as roles e atributos customizados
        return new DefaultOAuth2User(authorities, attributes, "email");
    }
}