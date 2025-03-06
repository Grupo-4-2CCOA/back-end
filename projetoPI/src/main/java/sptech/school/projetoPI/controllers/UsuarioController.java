package sptech.school.projetoPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.entities.Usuario;
import sptech.school.projetoPI.repositories.UsuarioRepository;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<Usuario> signUser(@RequestBody Usuario usuario) {
        if(
            usuarioRepository.existsByCpf(usuario.getCpf()) ||
            usuarioRepository.existsByEmail(usuario.getEmail())
        ) {
            return ResponseEntity.status(409).build();
        }

        usuario.setId(null);
        return ResponseEntity.status(201).body(usuario);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        List<Usuario> users = usuarioRepository.findAll();

        if(users.isEmpty()) return ResponseEntity.status(204).build();

        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Integer id) {
        return ResponseEntity.of(usuarioRepository.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUserById(@RequestBody Usuario usuario, @PathVariable Integer id) {
        if(!usuarioRepository.existsById(id)) return ResponseEntity.status(404).build();

        usuario.setId(id);
        return ResponseEntity.status(200).body(usuarioRepository.save(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        if(!usuarioRepository.existsById(id)) return ResponseEntity.status(404).build();

        return ResponseEntity.status(204).build();
    }
}
