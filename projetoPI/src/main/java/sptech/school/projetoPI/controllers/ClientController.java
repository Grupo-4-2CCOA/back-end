package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.client.*;
import sptech.school.projetoPI.entities.Client;
import sptech.school.projetoPI.services.user.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<ClientResumeResponseDto> signClient(@Valid @RequestBody ClientRequestDto client) {
        Client tempClient = service.signClient(ClientMapper.toEntity(client));
        return ResponseEntity.status(201).body(ClientMapper.toResumeResponseDto(tempClient));
    }

    @PostMapping("/login")
    public ResponseEntity<ClientTokenDto> login(@RequestBody ClientLoginDto usuarioLoginDto) {

        final Client user = ClientMapper.of(usuarioLoginDto);
        ClientTokenDto usuarioTokenDto = this.service.autenticar(user);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @GetMapping
    public ResponseEntity<List<ClientResumeResponseDto>> getAllClients() {
        List<Client> Clients = service.getAllClients();

        if (Clients.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(Clients.stream().map(ClientMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDto> getClientById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(ClientMapper.toResponseDto(service.getClientById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResumeResponseDto> updateClientById(@Valid @RequestBody ClientRequestDto Client, @PathVariable Integer id) {
        Client tempClient = service.updateClientById(ClientMapper.toEntity(Client), id);
        return ResponseEntity.status(200).body(ClientMapper.toResumeResponseDto(tempClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Integer id) {
        service.deleteClientById(id);
        return ResponseEntity.status(204).build();
    }
}
