package sptech.school.projetoPI.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.client.ClientMapper;
import sptech.school.projetoPI.dto.client.ClientRequestDto;
import sptech.school.projetoPI.dto.client.ClientResponseDto;
import sptech.school.projetoPI.dto.client.ClientResumeResponseDto;
import sptech.school.projetoPI.dto.login.UserLoginDto;
import sptech.school.projetoPI.dto.login.UserMapper;
import sptech.school.projetoPI.dto.login.UserTokenDto;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.services.user.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Cliente", description = "Endpoints para gerenciar clientes")
public class ClientController {

    private final ClientService service;

    @PostMapping
    @Operation(summary = "Cadastrar um cliente", description = "Cadastra um novo cliente no sistema.")
    public ResponseEntity<ClientResumeResponseDto> signClient(@Valid @RequestBody ClientRequestDto client) {
        Client tempClient = service.signClient(ClientMapper.toEntity(client));
        return ResponseEntity.status(201).body(ClientMapper.toResumeResponseDto(tempClient));
    }

    @PostMapping("/login")
    @Operation(summary = "xxxxx", description = "xxxxx")
    public ResponseEntity<UserTokenDto> login(@RequestBody UserLoginDto usuarioLoginDto) {

        System.out.println("Email: " + usuarioLoginDto.getEmail());
        System.out.println("Senha: " + usuarioLoginDto.getSenha());

        final Client usuario = UserMapper.of(usuarioLoginDto);
        UserTokenDto usuarioTokenDto = this.service.autenticar(usuario);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @GetMapping
    @Operation(summary = "Buscar clientes", description = "Busca todos os clientes cadastrados no sistema.")
    public ResponseEntity<List<ClientResumeResponseDto>> getAllClients() {
        List<Client> Clients = service.getAllClients();

        if (Clients.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(Clients.stream().map(ClientMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Busca o cliente com base no ID fornecido.")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ClientMapper.toResponseDto(service.getClientById(id)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente por ID", description = "Atualiza um cliente com base no ID fornecido.")
    public ResponseEntity<ClientResumeResponseDto> updateClientById(@Valid @RequestBody ClientRequestDto Client, @PathVariable Integer id) {
        Client tempClient = service.updateClientById(ClientMapper.toEntity(Client), id);
        return ResponseEntity.status(200).body(ClientMapper.toResumeResponseDto(tempClient));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente por ID", description = "Deleta um cliente com base no ID fornecido.")
    public ResponseEntity<Void> deleteClientById(@PathVariable Integer id) {
        service.deleteClientById(id);
        return ResponseEntity.status(204).build();
    }
}
