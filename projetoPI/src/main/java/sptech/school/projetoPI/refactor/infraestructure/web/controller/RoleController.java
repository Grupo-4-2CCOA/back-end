package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.refactor.core.application.command.role.CreateRoleCommand;
import sptech.school.projetoPI.refactor.core.application.command.role.DeleteRoleByIdCommand;
import sptech.school.projetoPI.refactor.core.application.command.role.GetRoleByIdCommand;
import sptech.school.projetoPI.refactor.core.application.command.role.UpdateRoleByIdCommand;
import sptech.school.projetoPI.refactor.core.application.usecase.role.*;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.infraestructure.mapper.RoleMapper;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role.CreateRoleRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role.DeleteRoleByNameRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role.GetRoleByNameRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role.UpdateRoleByNameRequestDto;

@RestController
@RequestMapping("/cargos")
@RequiredArgsConstructor
public class RoleController {
  private final CreateRoleUsecase createRoleUsecase;
  private final DeleteRoleByIdUsecase deleteRoleByIdUsecase;
  private final UpdateRoleByIdUsecase updateRoleByIdUsecase;
  private final GetRoleByIdUsecase getRoleByIdUsecase;
  private final GetRoleByNameUsecase getRoleByNameUsecase;

  @GetMapping
  public ResponseEntity<RoleDomain> getRoleByName(@RequestBody @Valid GetRoleByNameRequestDto getRoleByNameRequestDto) {
    GetRoleByIdCommand getRoleByIdCommand = RoleMapper.toGetRoleByNameCommand(getRoleByNameRequestDto);
    RoleDomain roleDomain = this.getRoleByIdUsecase.execute(getRoleByIdCommand);
    return ResponseEntity.status(200).body(roleDomain);
  }

  @PostMapping
  public ResponseEntity<Void> createRole(@RequestBody @Valid CreateRoleRequestDto createRoleRequestDto) {
    CreateRoleCommand createRoleCommand = RoleMapper.toCreateRoleCommand(createRoleRequestDto);
    createRoleUsecase.execute(createRoleCommand);
    return ResponseEntity.status(201).build();
  }

  @PutMapping
  public ResponseEntity<Void> updateRoleByName(@RequestBody @Valid UpdateRoleByNameRequestDto updateRoleByNameRequestDto) {
    UpdateRoleByIdCommand updateRoleByIdCommand = RoleMapper.toUpdateRoleByNameCommand(updateRoleByNameRequestDto);
    this.updateRoleByIdUsecase.execute(updateRoleByIdCommand);
    return ResponseEntity.status(200).build();
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteRoleByName(@RequestBody @Valid DeleteRoleByNameRequestDto deleteRoleByNameRequestDto) {
    DeleteRoleByIdCommand deleteRoleByIdCommand = RoleMapper.toDeleteRoleByNameCommand(deleteRoleByNameRequestDto);
    this.deleteRoleByIdUsecase.execute(deleteRoleByIdCommand);
    return ResponseEntity.status(204).build();
  }
}
