package sptech.school.projetoPI.refactor.infraestructure.web.dto.role;


import sptech.school.projetoPI.refactor.core.domain.aggregate.UserDomain;

import java.util.Set;

public record GetRoleByNameResponseDto(String name, String description, Set<UserDomain> users) {
}
