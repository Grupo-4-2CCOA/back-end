package sptech.school.projetoPI.refactor.infraestructure.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.projetoPI.refactor.core.application.command.role.*;
import sptech.school.projetoPI.refactor.core.application.usecase.role.*;
import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;
import sptech.school.projetoPI.refactor.infraestructure.mapper.RoleMapper;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role.*;
import sptech.school.projetoPI.refactor.infraestructure.web.dto.response.role.RoleResponseDto;

@RestController
@RequestMapping("/cargos")
@RequiredArgsConstructor
public class RoleController {
  private final GetRoleByIdUsecase getRoleByIdUsecase;
  private final GetRoleByNameUsecase getRoleByNameUsecase;
  private final CreateRoleUsecase createRoleUsecase;
  private final DeleteRoleByIdUsecase deleteRoleByIdUsecase;
  private final UpdateRoleByIdUsecase updateRoleByIdUsecase;

  @GetMapping("/{id}")
  public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Integer id) {
    GetRoleByIdCommand getRoleByIdCommand = new GetRoleByIdCommand(id);
    RoleResponseDto roleResponseDto = RoleMapper.toRoleResponseDto(this.getRoleByIdUsecase.execute(getRoleByIdCommand));
    return ResponseEntity.status(200).body(roleResponseDto);
  }

  @GetMapping("/nome")
  public ResponseEntity<RoleResponseDto> getRoleByName(@RequestParam(name = "name") String name) {
    GetRoleByNameCommand getRoleByNameCommand = new GetRoleByNameCommand(name);
    RoleResponseDto roleResponseDto = RoleMapper.toRoleResponseDto(this.getRoleByNameUsecase.execute(getRoleByNameCommand));
    return ResponseEntity.status(200).body(roleResponseDto);
  }

  @PostMapping
  public ResponseEntity<RoleResponseDto> createRole(@RequestBody @Valid CreateRoleRequestDto createRoleRequestDto) {
    CreateRoleCommand createRoleCommand = RoleMapper.toCreateRoleCommand(createRoleRequestDto);
    RoleResponseDto roleResponseDto = RoleMapper.toRoleResponseDto(this.createRoleUsecase.execute(createRoleCommand));
    return ResponseEntity.status(201).body(roleResponseDto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RoleResponseDto> updateRoleById(@PathVariable Integer id, @RequestBody @Valid UpdateRoleByIdRequestDto updateRoleByIdRequestDto) {
    UpdateRoleByIdCommand updateRoleByIdCommand = RoleMapper.toUpdateRoleByIdCommand(updateRoleByIdRequestDto, id);
    RoleResponseDto roleResponseDto = RoleMapper.toRoleResponseDto(this.updateRoleByIdUsecase.execute(updateRoleByIdCommand));
    return ResponseEntity.status(200).body(roleResponseDto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRoleById(@PathVariable Integer id) {
    DeleteRoleByIdCommand deleteRoleByIdCommand = new DeleteRoleByIdCommand(id);
    this.deleteRoleByIdUsecase.execute(deleteRoleByIdCommand);
    return ResponseEntity.status(204).build();
  }
}
