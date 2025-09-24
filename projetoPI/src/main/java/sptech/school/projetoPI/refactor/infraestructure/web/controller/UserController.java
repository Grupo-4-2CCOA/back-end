package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.projetoPI.refactor.core.application.command.user.CreateUserCommand;
import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;
import sptech.school.projetoPI.refactor.infraestructure.mapper.UserMapper;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.user.CreateUserResponseDto;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UserController {

  @PostMapping
  public ResponseEntity<CreateUserResponseDto> createUser(@RequestBody @Valid CreateUserCommand request){
      UserDomain userDomain = UserMapper.toDomain(request);
      return ResponseEntit(userDomain);

  }
}
