package sptech.school.projetoPI.refactor.infraestructure.web.dto.response.user;

import sptech.school.projetoPI.refactor.infraestructure.web.dto.response.role.RoleResponseDto;

public record UserResponseDto(Integer id, String name, String email, String cpf, String phone, String cep, RoleResponseDto role) {
}
