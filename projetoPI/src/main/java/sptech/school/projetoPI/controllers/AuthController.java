package sptech.school.projetoPI.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.projetoPI.dto.login.UserLoginDto;
import sptech.school.projetoPI.dto.login.UserMapper;
import sptech.school.projetoPI.dto.login.UserTokenDto;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.services.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    @Operation(summary = "xxxxx", description = "xxxxx")
    public ResponseEntity<UserTokenDto> login(@RequestBody UserLoginDto usuarioLoginDto) {

        UserTokenDto usuarioTokenDto = service.autenticar(usuarioLoginDto, authenticationManager);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }
}
