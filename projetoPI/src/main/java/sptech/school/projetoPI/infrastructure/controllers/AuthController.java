package sptech.school.projetoPI.infrastructure.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.core.domains.RoleDomain;
import sptech.school.projetoPI.infrastructure.config.auth.JwtService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Value("${web-endpoint.url}")
    private String webEndpoint;

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/oauth2/success")
    public void oauth2Success(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        Authentication authentication = (Authentication) request.getSession()
                .getAttribute("OAUTH2_AUTHENTICATION");

        if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User)) {
            response.sendRedirect(String.format("%s/login?error=auth_failed", webEndpoint));
            return;
        }

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        String email = oauth2User.getAttribute("email");
        RoleDomain roleDomain = oauth2User.getAttribute("role");
        String role = roleDomain.getName();
        Integer clientId = oauth2User.getAttribute("id");

        String token = jwtService.generateToken(email, role, clientId);
        String sameSite = "Lax";
        
        ResponseCookie authCookie = ResponseCookie.from("AUTH_TOKEN", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(86400) // 1 dia em segundos
                .sameSite(sameSite)
                .build();
        
        response.addHeader(HttpHeaders.SET_COOKIE, authCookie.toString());

        // 4. Configura um cookie adicional para o frontend (opcional)
        ResponseCookie userRoleCookie = ResponseCookie.from("USER_ROLE", role)
                .path("/")
                .maxAge(86400)
                .sameSite(sameSite)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, userRoleCookie.toString());

        // 5. Redireciona para a página de loading do frontend
        response.sendRedirect(String.format("%s/auth-loading", webEndpoint));
    }

    @GetMapping("/check-auth")
    public ResponseEntity<Map<String, String>> checkAuth(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @CookieValue(name = "AUTH_TOKEN", required = false) String token) {
        Map<String, String> response = new HashMap<>();

        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        }
        else if (token != null && !token.isEmpty()) {
            jwt = token;
        }

        if (jwt != null && jwtService.isTokenValid(jwt)) {
            response.put("status", "authenticated");
            response.put("role", jwtService.extractClaim(jwt, claims -> claims.get("role", String.class)));
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

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        }
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
        String sameSite = "Lax";
        
        ResponseCookie authCookie = ResponseCookie.from("AUTH_TOKEN", "")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(0)
                .sameSite(sameSite)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, authCookie.toString());

        SecurityContextHolder.clearContext();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/user-info")
    public ResponseEntity<Map<String, Object>> getUserInfo(
            HttpServletRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @CookieValue(name = "AUTH_TOKEN", required = false) String token) {

        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
        }
        else if (token != null && !token.isEmpty()) {
            jwt = token;
        }

        System.out.println("Token recebido: " + (jwt != null ? "SIM" : "NÃO"));
        System.out.println("Token do cookie: " + (token != null ? "SIM" : "NÃO"));
        System.out.println("Token do header: " + (authHeader != null ? "SIM" : "NÃO"));

        if (jwt == null || jwt.isEmpty()) {
            System.out.println("Token Null ou vazio");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            System.out.println("Entrei no Try");
            if (!jwtService.isTokenValid(jwt)) {
                System.out.println("Token não valido");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String email = jwtService.extractUsername(jwt);
            String role = jwtService.extractClaim(jwt, claims -> claims.get("role", String.class));
            Integer clientId = jwtService.extractClaim(jwt, claims -> claims.get("id", Integer.class));

            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", email);
            userInfo.put("role", role);
            if (clientId != null) {
                userInfo.put("id", clientId);
            }

            return ResponseEntity.ok(userInfo);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("ERRO INTERNO ---- %s", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}