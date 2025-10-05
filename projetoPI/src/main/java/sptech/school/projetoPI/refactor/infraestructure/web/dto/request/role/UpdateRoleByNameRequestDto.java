package sptech.school.projetoPI.refactor.infraestructure.web.dto.request.role;


import java.util.Optional;

public record UpdateRoleByNameRequestDto(String searchName, String newName, Optional<String> newDescription) {
}
