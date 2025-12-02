package sptech.school.projetoPI.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import sptech.school.projetoPI.infrastructure.config.auth.AuthService;
import sptech.school.projetoPI.infrastructure.config.auth.CustomOAuth2UserService;
import sptech.school.projetoPI.infrastructure.config.auth.HttpCookieOAuth2AuthorizationRequestRepository;
import sptech.school.projetoPI.infrastructure.config.auth.JwtAuthenticationFilter;
import sptech.school.projetoPI.infrastructure.config.auth.JwtService;

@TestConfiguration
@EnableWebSecurity
public class TestSecurityConfig {

    // Mockar todos os componentes de segurança que são carregados automaticamente
    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private CustomOAuth2UserService customOAuth2UserService;

    @MockBean
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Bean
    @Primary
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}

