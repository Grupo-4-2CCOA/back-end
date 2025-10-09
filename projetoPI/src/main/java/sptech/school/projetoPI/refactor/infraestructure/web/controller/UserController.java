package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.user.CreateUserRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.response.role.RoleResponseDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.response.user.UserResponseDto;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UserController {
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getClientById(@PathVariable Integer id) {
    UserResponseDto userResponseDto = new UserResponseDto(
      id,
      "nome",
      "email@email.com",
      "12345678901",
      "5511912345678",
      "12345-123",
      new RoleResponseDto(
        2,
        "Cliente",
        "Cliente do salão de beleza"
      )
    );
    return ResponseEntity.ok(userResponseDto);
  }
  @PostMapping()
  public ResponseEntity<UserResponseDto> createClient(@RequestBody CreateUserRequestDto createUserRequestDto) {
    UserResponseDto userResponseDto = new UserResponseDto(
      1,
      createUserRequestDto.name(),
      createUserRequestDto.email(),
      "12345678901",
      "5511912345678",
      "12345-123",
      new RoleResponseDto(
        2,
        "Cliente",
        "Cliente do salão de beleza"
      )
    );
    return ResponseEntity.ok(userResponseDto);
  }
}
