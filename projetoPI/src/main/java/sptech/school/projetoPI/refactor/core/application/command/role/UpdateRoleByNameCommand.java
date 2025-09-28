package sptech.school.projetoPI.refactor.core.application.command.role;


import java.util.Optional;

public record UpdateRoleByNameCommand(String searchName, String newName, Optional<String> newDescription) {
}
