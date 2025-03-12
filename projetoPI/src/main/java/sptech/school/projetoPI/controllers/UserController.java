package sptech.school.projetoPI.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.entities.User;
import sptech.school.projetoPI.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<User> signUser(@RequestBody User user) {
        return ResponseEntity.status(201).body(service.signUser(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.updateUserById(user, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        return service.deleteUserById(id);
    }
}
