package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.refactor.core.application.command.role.CreateRoleCommand;
import sptech.school.projetoPI.refactor.core.application.command.role.DeleteRoleByNameCommand;
import sptech.school.projetoPI.refactor.core.application.command.role.GetRoleByNameCommand;
import sptech.school.projetoPI.refactor.core.application.command.role.UpdateRoleByNameCommand;
import sptech.school.projetoPI.refactor.core.application.usecase.role.CreateRoleUsecase;
import sptech.school.projetoPI.refactor.core.application.usecase.role.DeleteRoleByNameUsecase;
import sptech.school.projetoPI.refactor.core.application.usecase.role.GetRoleByNameUsecase;
import sptech.school.projetoPI.refactor.core.application.usecase.role.UpdateRoleByNameUsecase;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.infraestructure.mapper.RoleMapper;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.role.CreateRoleRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.role.DeleteRoleByNameRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.role.GetRoleByNameRequestDto;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.role.UpdateRoleByNameRequestDto;

@RestController
@RequestMapping("/cargos")
@RequiredArgsConstructor
public class RoleController {
  private final CreateRoleUsecase createRoleUsecase;
  private final DeleteRoleByNameUsecase deleteRoleByNameUsecase;
  private final UpdateRoleByNameUsecase updateRoleByNameUsecase;
  private final GetRoleByNameUsecase getRoleByNameUsecase;

  @GetMapping
  public ResponseEntity<RoleDomain> getRoleByName(@RequestBody @Valid GetRoleByNameRequestDto getRoleByNameRequestDto) {
    GetRoleByNameCommand getRoleByNameCommand = RoleMapper.toGetRoleByNameCommand(getRoleByNameRequestDto);
    RoleDomain roleDomain = this.getRoleByNameUsecase.execute(getRoleByNameCommand);
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
    UpdateRoleByNameCommand updateRoleByNameCommand = RoleMapper.toUpdateRoleByNameCommand(updateRoleByNameRequestDto);
    this.updateRoleByNameUsecase.execute(updateRoleByNameCommand);
    return ResponseEntity.status(200).build();
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteRoleByName(@RequestBody @Valid DeleteRoleByNameRequestDto deleteRoleByNameRequestDto) {
    DeleteRoleByNameCommand deleteRoleByNameCommand = RoleMapper.toDeleteRoleByNameCommand(deleteRoleByNameRequestDto);
    this.deleteRoleByNameUsecase.execute(deleteRoleByNameCommand);
    return ResponseEntity.status(204).build();
  }
}
