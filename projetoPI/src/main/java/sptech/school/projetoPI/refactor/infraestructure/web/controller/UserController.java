package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UserController {

//  @PostMapping
//  public ResponseEntity<CreateClientResponseDto> createClient(@RequestBody @Valid CreateClientRequestDto userRequestDto){
//      CreateClientResponseDto userResponseDto = UserMapper.toCreateClientResponseDto(userRequestDto);
//      return ResponseEntity.status(201).body(userResponseDto);
//  }
}
