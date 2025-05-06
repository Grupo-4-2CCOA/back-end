package sptech.school.projetoPI.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.projetoPI.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.dto.login.UserLoginDto;
import sptech.school.projetoPI.dto.login.UserMapper;
import sptech.school.projetoPI.dto.login.UserTokenDto;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.exceptions.ErroResponseExamples;
import sptech.school.projetoPI.services.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Login", description = "Endpoints para gerenciar o login")
public class AuthController {

    private final AuthService service;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    @Operation(summary = "Login", description = "Endpoint para autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário logado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.CREATED)
            )),
            @ApiResponse(responseCode = "400", description = "Um ou mais campos estão inválidos", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ClientResumeResponseDto.class),
                    examples = @ExampleObject(value = ErroResponseExamples.BAD_REQUEST)
            )),
    })
    public ResponseEntity<UserTokenDto> login(@RequestBody UserLoginDto usuarioLoginDto) {

        UserTokenDto usuarioTokenDto = service.autenticar(usuarioLoginDto, authenticationManager);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }
}
