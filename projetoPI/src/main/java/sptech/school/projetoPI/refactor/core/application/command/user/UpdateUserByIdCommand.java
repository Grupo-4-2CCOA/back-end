package sptech.school.projetoPI.refactor.core.application.command.user;

public record UpdateUserByIdCommand(Integer id, String name, String description) {
}
