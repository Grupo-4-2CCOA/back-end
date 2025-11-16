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
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
        
        // Log para verificar se o cookie está sendo definido
        System.out.println("=== COOKIE DEFINIDO NO /oauth2/success ===");
        System.out.println("Cookie string: " + authCookie.toString());
        System.out.println("Token gerado: " + token.substring(0, Math.min(20, token.length())) + "...");
        System.out.println("Redirecionando para: " + String.format("%s/auth-loading", webEndpoint));
        System.out.println("===========================================");

        // 4. Configura um cookie adicional para o frontend (opcional)
        ResponseCookie userRoleCookie = ResponseCookie.from("USER_ROLE", role)
                .path("/")
                .maxAge(86400)
                .sameSite(sameSite)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, userRoleCookie.toString());

        // 5. Redireciona para a página de loading do frontend
        // IMPORTANTE: Passa o token como query parameter para garantir que o frontend tenha acesso
        // Isso é necessário porque cookies cross-domain podem não funcionar em requisições AJAX
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
        String redirectUrl = String.format("%s/auth-loading?token=%s", webEndpoint, encodedToken);
        response.sendRedirect(redirectUrl);
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

        // Logs detalhados para diagnóstico
        System.out.println("=== DIAGNÓSTICO /auth/user-info ===");
        System.out.println("Request URL: " + request.getRequestURL());
        System.out.println("Request Origin: " + request.getHeader("Origin"));
        System.out.println("Request Referer: " + request.getHeader("Referer"));
        System.out.println("Authorization header: " + (authHeader != null ? authHeader.substring(0, Math.min(20, authHeader.length())) + "..." : "null"));
        System.out.println("Cookie AUTH_TOKEN: " + (token != null ? "SIM (length: " + token.length() + ")" : "NÃO"));
        
        // Verifica todos os cookies recebidos
        if (request.getCookies() != null) {
            System.out.println("Total de cookies recebidos: " + request.getCookies().length);
            for (Cookie c : request.getCookies()) {
                System.out.println("  - Cookie: " + c.getName() + " = " + (c.getValue() != null ? c.getValue().substring(0, Math.min(20, c.getValue().length())) + "..." : "null"));
            }
        } else {
            System.out.println("Nenhum cookie recebido!");
        }

        String jwt = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            System.out.println("Token extraído do header Authorization");
        }
        else if (token != null && !token.isEmpty()) {
            jwt = token;
            System.out.println("Token extraído do cookie");
        }

        System.out.println("Token final: " + (jwt != null ? "SIM" : "NÃO"));
        System.out.println("================================");

        if (jwt == null || jwt.isEmpty()) {
            System.out.println("Token Null ou vazio [UPDATED]");
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