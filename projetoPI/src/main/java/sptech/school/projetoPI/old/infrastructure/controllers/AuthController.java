package sptech.school.projetoPI.old.infrastructure.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.old.infrastructure.auth.JwtService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/oauth2/success")
    public void oauth2Success(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        // 1. Recupera a autenticação
        Authentication authentication = (Authentication) request.getSession()
                .getAttribute("OAUTH2_AUTHENTICATION");

        if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User)) {
            response.sendRedirect("http://localhost:5173/login?error=auth_failed");
            return;
        }

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        String role = oauth2User.getAttribute("role");
        Integer clientId = oauth2User.getAttribute("id");

        // 2. Gera o token JWT
        String token = jwtService.generateToken(email, role, clientId);

        // 3. Configura o cookie
        Cookie authCookie = new Cookie("AUTH_TOKEN", token);
        authCookie.setHttpOnly(true);
        authCookie.setSecure(false); // Ativar em produção
        authCookie.setPath("/");
        authCookie.setMaxAge(86400); // 1 dia em segundos
        response.addCookie(authCookie);

        // 4. Configura um cookie adicional para o frontend (opcional)
        Cookie userRoleCookie = new Cookie("USER_ROLE", role);
        userRoleCookie.setPath("/");
        userRoleCookie.setMaxAge(86400);
        response.addCookie(userRoleCookie);

        // 5. Redireciona para a página de loading do frontend
        response.sendRedirect("http://localhost:5173/auth-loading");
    }

    @GetMapping("/check-auth")
    public ResponseEntity<Map<String, String>> checkAuth(
            @CookieValue(name = "AUTH_TOKEN", required = false) String token) {
        Map<String, String> response = new HashMap<>();

        if (token != null && jwtService.isTokenValid(token)) {
            response.put("status", "authenticated");
            response.put("role", jwtService.extractClaim(token, claims -> claims.get("role", String.class)));
            return ResponseEntity.ok(response);
        }

        response.put("status", "unauthenticated");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @CookieValue(name = "AUTH_TOKEN", required = false) String token) {

        String jwt = null;

        // 1. Tenta pegar do header Authorization
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        }
        // 2. Tenta pegar do cookie
        else if (token != null) {
            jwt = token;
        }

        if (jwt == null || !jwtService.isTokenValid(jwt)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        // 1. Limpa o cookie de autenticação
        Cookie authCookie = new Cookie("AUTH_TOKEN", null);
        authCookie.setPath("/");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(0); // Expira imediatamente
        response.addCookie(authCookie);

        // 2. Invalida a sessão do Spring Security
        SecurityContextHolder.clearContext();

        // 3. Invalida a sessão HTTP (se estiver usando)
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(
            @CookieValue(name = "AUTH_TOKEN", required = false) String token) {
        // 1. Verifica se o token existe
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2. Valida o token
        try {
            if (!jwtService.isTokenValid(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // 3. Extrai as informações
            String email = jwtService.extractUsername(token);
            String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));
            Integer clientId = jwtService.extractClaim(token, claims -> claims.get("id", Integer.class));

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", email);
            userInfo.put("role", role);
            if (clientId != null) {
                userInfo.put("id", clientId);
            }

            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}