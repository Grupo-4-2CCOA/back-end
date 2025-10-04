package sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role;


import sptech.school.projetoPI.refactor.core.domain.aggregate.RoleDomain;

public record CreateRoleRequestDto(String name, String description) {
}
