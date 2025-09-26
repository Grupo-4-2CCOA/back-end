package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.refactor.infraestructure.mapper.UserMapper;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.user.CreateClientRequestDto;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UserController {

  @PostMapping
  public ResponseEntity<CreateClientResponseDto> createClient(@RequestBody @Valid CreateClientRequestDto userRequestDto){
      CreateClientResponseDto userResponseDto = UserMapper.toCreateClientResponseDto(userRequestDto);
      return ResponseEntity.status(201).body(userResponseDto);
  }
}
