package sptech.school.projetoPI.refactor.core.application.command.user;

public record CreateUserCommand(String name, String email, String cpf, String phone, String cep, Integer roleId) {
}
