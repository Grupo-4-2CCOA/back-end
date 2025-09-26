package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.projetoPI.refactor.core.application.command.role.CreateRoleCommand;
import sptech.school.projetoPI.refactor.core.application.usecase.role.CreateRoleUsecase;
import sptech.school.projetoPI.refactor.infraestructure.mapper.RoleMapper;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.role.CreateRoleRequestDto;

@RestController
@RequestMapping("/cargos")
@RequiredArgsConstructor
public class RoleController {
  private final CreateRoleUsecase createRoleUsecase;

  @PostMapping
  public ResponseEntity<Void> createRole(@RequestBody @Valid CreateRoleRequestDto roleRequestDto){
    CreateRoleCommand createRoleCommand = RoleMapper.toCreateRoleCommand(roleRequestDto);
    createRoleUsecase.execute(createRoleCommand);
    return ResponseEntity.status(201).build();
  }
}
