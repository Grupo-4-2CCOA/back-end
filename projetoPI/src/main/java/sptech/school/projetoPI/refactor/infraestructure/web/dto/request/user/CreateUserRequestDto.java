package sptech.school.projetoPI.refactor.infraestructure.web.dto.request.user;

public record CreateUserRequestDto(String name, String email, String cpf, String phone, String cep, Integer fkRole) {
}
