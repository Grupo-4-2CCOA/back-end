package sptech.school.projetoPI.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.dto.user.UserMapper;
import sptech.school.projetoPI.dto.user.UserRequestDto;
import sptech.school.projetoPI.dto.user.UserResponseDto;
import sptech.school.projetoPI.dto.user.UserResumeResponseDto;
import sptech.school.projetoPI.entities.User;
import sptech.school.projetoPI.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResumeResponseDto> signUser(@Valid @RequestBody UserRequestDto user) {
        User tempUser = service.signUser(UserMapper.toEntity(user));
        return ResponseEntity.status(201).body(UserMapper.toResumeResponseDto(tempUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResumeResponseDto>> getAllUsers() {
        List<User> users = service.getAllUsers();

        if (users.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(users.stream().map(UserMapper::toResumeResponseDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(UserMapper.toResponseDto(service.getUserById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResumeResponseDto> updateUserById(@Valid @RequestBody UserRequestDto user, @PathVariable Integer id) {
        User tempUser = service.updateUserById(UserMapper.toEntity(user), id);
        return ResponseEntity.status(200).body(UserMapper.toResumeResponseDto(tempUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        service.deleteUserById(id);
        return ResponseEntity.status(204).build();
    }
}
